package com.vegastore.jitarger.implement;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.vegastore.jitarger.dto.base.PedidoDTO;
import com.vegastore.jitarger.dto.create.CreatePedidoDTO;
import com.vegastore.jitarger.dto.update.UpdatePedidoDTO;
import com.vegastore.jitarger.exception.RecursoNoEncontradoException;
import com.vegastore.jitarger.service.PedidoService;
import com.vegastore.jitarger.util.DynamicSqlBuilder;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger log = LoggerFactory.getLogger(PedidoServiceImpl.class);

    private final RowMapper<PedidoDTO> pedidoRowMapper = (rs, rowNum) -> PedidoDTO.builder()
            .id(rs.getLong("id"))
            .idUsuario(rs.getLong("id_usuario"))
            .fechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime())
            .estado(rs.getString("estado"))
            .total(rs.getBigDecimal("total"))
            .subtotal(rs.getBigDecimal("subtotal"))
            .descuento(rs.getBigDecimal("descuento"))
            .impuestos(rs.getBigDecimal("impuestos"))
            .metodoPago(rs.getString("metodo_pago"))
            .direccionEntrega(rs.getString("direccion_entrega"))
            .fechaConfirmacion(rs.getTimestamp("fecha_confirmacion") != null
                    ? rs.getTimestamp("fecha_confirmacion").toLocalDateTime()
                    : null)
            .fechaEntrega(rs.getTimestamp("fecha_entrega") != null ? rs.getTimestamp("fecha_entrega").toLocalDateTime()
                    : null)
            .cancelado(rs.getBoolean("cancelado"))
            .fechaCancelacion(rs.getTimestamp("fecha_cancelacion") != null
                    ? rs.getTimestamp("fecha_cancelacion").toLocalDateTime()
                    : null)
            .build();

    private List<PedidoDTO> ejecutarConsultaListaPedidos(String sql, Object... parametros) {
        try {
            return jdbcTemplate.query(sql, pedidoRowMapper, parametros);
        } catch (EmptyResultDataAccessException e) {
            log.warn("No se encontraron resultados para la consulta: {}", e.getMessage());
            throw new RecursoNoEncontradoException("Pedidos", "filtro aplicado", null);
        }
    }

    @Override
    public List<PedidoDTO> obtenerTodosLosPedidos() {
        return ejecutarConsultaListaPedidos("SELECT * FROM pedido ORDER BY id");
    }

    @Override
    public List<PedidoDTO> obtenerPedidos(int pagina) {
        int offset = (pagina - 1) * 10;
        return ejecutarConsultaListaPedidos("SELECT * FROM pedido ORDER BY id LIMIT 10 OFFSET ?", offset);
    }

    @Override
    public List<PedidoDTO> obtenerPedidosPorUsuario(int pagina, long idUsuario) {
        int offset = (pagina - 1) * 10;
        return ejecutarConsultaListaPedidos("SELECT * FROM pedido WHERE id_usuario = ? ORDER BY id LIMIT 10 OFFSET ?",
                idUsuario, offset);
    }

    @Override
    public List<PedidoDTO> obtenerPedidosPorUsuarioPorEstado(int pagina, long idUsuario, String estado) {
        int offset = (pagina - 1) * 10;
        return ejecutarConsultaListaPedidos(
                "SELECT * FROM pedido WHERE id_usuario = ? AND estado = ? ORDER BY id LIMIT 10 OFFSET ?", idUsuario,
                estado, offset);
    }

    @Override
    public List<PedidoDTO> obtenerPedidosPorEstado(int pagina, String estado) {
        int offset = (pagina - 1) * 10;
        return ejecutarConsultaListaPedidos("SELECT * FROM pedido WHERE estado = ? ORDER BY id LIMIT 10 OFFSET ?",
                estado, offset);
    }

    @Override
    public PedidoDTO obtenerPedidoPorId(long id) {
        String sql = "SELECT * FROM pedido WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, pedidoRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecursoNoEncontradoException("Pedido", "ID", id);
        }
    }

    @Override
    public PedidoDTO crearPedido(CreatePedidoDTO pedidoDTO) {

        LocalDateTime now = LocalDateTime.now();

        Map<String, Object> fields = new LinkedHashMap<>();

        fields.put("id_usuario", pedidoDTO.getIdUsuario());
        fields.put("fecha_registro", Timestamp.valueOf(now));
        fields.put("estado", pedidoDTO.getEstado());
        fields.put("total", pedidoDTO.getTotal());
        fields.put("subtotal", pedidoDTO.getSubtotal());
        fields.put("descuento", pedidoDTO.getDescuento());
        fields.put("impuestos", pedidoDTO.getImpuestos());
        fields.put("metodo_pago", pedidoDTO.getMetodoPago());
        fields.put("direccion_entrega", pedidoDTO.getDireccionEntrega());

        String sql = DynamicSqlBuilder.buildInsertSql("pedido", fields);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        log.info("Creando pedido con datos: {}", fields);

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int i = 1;
            for (Object value : fields.values()) {
                ps.setObject(i++, value);
            }
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();

        if (generatedId == null) {
            log.error("No se pudo obtener el ID del pedido creado.");
            throw new RuntimeException("No se pudo obtener el ID del pedido creado.");
        }

        log.info("Pedido creado con ID: {}", generatedId);

        return obtenerPedidoPorId(generatedId.longValue());
    }

    @Override
    public void actualizarPedido(long id, UpdatePedidoDTO dto) {

        if (!existePedido(id)) {
            log.warn("No se encontró el pedido con ID: {}", id);
            throw new RecursoNoEncontradoException("Pedido", "ID", id);
        }

        Map<String, Object> fields = new LinkedHashMap<>();

        if (dto.getEstado() != null)
            fields.put("estado", dto.getEstado());
        if (dto.getFechaConfirmacion() != null)
            fields.put("fecha_confirmacion", Timestamp.valueOf(dto.getFechaConfirmacion()));
        if (dto.getFechaEntrega() != null)
            fields.put("fecha_entrega", Timestamp.valueOf(dto.getFechaEntrega()));
        if (dto.getCancelado() != null)
            fields.put("cancelado", dto.getCancelado());
        if (dto.getFechaCancelacion() != null)
            fields.put("fecha_cancelacion", Timestamp.valueOf(dto.getFechaCancelacion()));
        if (dto.getMetodoPago() != null)
            fields.put("metodo_pago", dto.getMetodoPago());
        if (dto.getDireccionEntrega() != null)
            fields.put("direccion_entrega", dto.getDireccionEntrega());
        if (dto.getSubtotal() != null)
            fields.put("subtotal", dto.getSubtotal());
        if (dto.getDescuento() != null)
            fields.put("descuento", dto.getDescuento());
        if (dto.getImpuestos() != null)
            fields.put("impuestos", dto.getImpuestos());
        if (dto.getTotal() != null)
            fields.put("total", dto.getTotal());

        if (fields.isEmpty()){
            log.warn("No se proporcionaron campos para actualizar el pedido con ID: {}", id);
            return;
        }

        String sql = DynamicSqlBuilder.buildUpdateSql("pedido", fields, "id = ?");

        List<Object> parametros = new ArrayList<>(fields.values());
        parametros.add(id);

        log.info("Actualizando pedido con ID {} con los siguientes campos: {}", id, fields);

        jdbcTemplate.update(sql, parametros.toArray());
    }

    @Override
    public void actualizarEstadoPedido(long id, String nuevoEstado, String comentario) {

        Map<String, Object> fields = new LinkedHashMap<>();

        fields.put("estado", nuevoEstado);
        fields.put("fecha_confirmacion", Timestamp.valueOf(LocalDateTime.now()));

        String sql = DynamicSqlBuilder.buildUpdateSql("pedido", fields, "id = ?");

        jdbcTemplate.update(sql, nuevoEstado, Timestamp.valueOf(LocalDateTime.now()), id);
    }

    @Override
    public void cancelarPedido(long id, String comentario) {

        Map<String, Object> fields = new LinkedHashMap<>();

        fields.put("cancelado", true);
        fields.put("fecha_cancelacion", Timestamp.valueOf(LocalDateTime.now()));

        String sql = DynamicSqlBuilder.buildUpdateSql("pedido", fields, "id = ?");

        jdbcTemplate.update(sql, true, Timestamp.valueOf(LocalDateTime.now()), id);
    }

    @Override
    public void borrarPedido(long id) {
        String sql = DynamicSqlBuilder.buildDeleteSql("pedido", "id = ?");
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existePedido(long id) {
        String sql = DynamicSqlBuilder.buildCountSql("pedido", "id = ?");
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public long contarPedidos() {
        String sql = DynamicSqlBuilder.buildCountSql("pedido");
        Long count = jdbcTemplate.queryForObject(sql, Long.class);

        if (count == null) {
            log.warn("El conteo de pedidos devolvió null, devolviendo 0");
            return 0L; // la L al final indica que es un long
        }

        return count;
    }

    @Override
    public long contarPedidosPorEstado(String estado) {
        String sql = DynamicSqlBuilder.buildCountSql("pedido", "estado = ?");
        Long count = jdbcTemplate.queryForObject(sql, Long.class, estado);

        if (count == null) {
            log.warn("El conteo de pedidos devolvió null, devolviendo 0");
            return 0L; // la L al final indica que es un long
        }

        return count;
    }

    @Override
    public long contarPedidosPorUsuario(long idUsuario) {
        String sql = DynamicSqlBuilder.buildCountSql("pedido", "id_usuario = ?");
        Long count = jdbcTemplate.queryForObject(sql, Long.class, idUsuario);

        if (count == null) {
            log.warn("El conteo de pedidos devolvió null, devolviendo 0");
            return 0L; // la L al final indica que es un long
        }

        return count;
    }

    @Override
    public long contarPedidosPorUsuarioYEstado(long idUsuario, String estado) {
        String sql = DynamicSqlBuilder.buildCountSql("pedido", "id_usuario = ? AND estado = ?");
        Long count = jdbcTemplate.queryForObject(sql, Long.class, idUsuario, estado);

        if (count == null) {
            log.warn("El conteo de pedidos devolvió null, devolviendo 0");
            return 0L; // la L al final indica que es un long
        }

        return count;
    }

}
