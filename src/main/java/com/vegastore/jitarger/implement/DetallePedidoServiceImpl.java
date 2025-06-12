package com.vegastore.jitarger.implement;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.vegastore.jitarger.dto.base.DetallePedidoDTO;
import com.vegastore.jitarger.dto.create.CreateDetallePedidoDTO;
import com.vegastore.jitarger.dto.update.UpdateDetallePedidoDTO;
import com.vegastore.jitarger.exception.RecursoNoEncontradoException;
import com.vegastore.jitarger.service.DetallePedidoService;
import com.vegastore.jitarger.util.DynamicSqlBuilder;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger log = LoggerFactory.getLogger(DetallePedidoServiceImpl.class);

    // RowMapper para mapear los resultados de la consulta a DetallePedidoDTO

    private final RowMapper<DetallePedidoDTO> detallePedidoRowMapper = (rs, rowNum) -> DetallePedidoDTO.builder()
            .id(rs.getLong("id"))
            .idPedido(rs.getLong("id_pedido"))
            .idProductoPresentacion(rs.getLong("id_producto_presentacion"))
            .idLote(rs.getLong("id_lote"))
            .cantidad(rs.getBigDecimal("cantidad"))
            .precioUnitario(rs.getBigDecimal("precio_unitario"))
            .subTotal(rs.getBigDecimal("subtotal"))
            .nombreProducto(rs.getString("nombre_producto"))
            .unidadmedidaPresentacion(rs.getString("unidad_medida_presentacion"))
            .build();


    
    @Override
    public List<DetallePedidoDTO> obtenerDetallePedidos(){
        String sql = "SELECT * FROM detalle_pedido";
        return jdbcTemplate.query(sql, detallePedidoRowMapper);
    }

    @Override
    public List<DetallePedidoDTO> obtenerDetallesPedidoPorPedido(int pagina,long pedidoId){
        int offset = (pagina - 1) * 10;
        String sql = "SELECT * FROM detalle_pedido WHERE id_pedido = ? LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, detallePedidoRowMapper, pedidoId, 10, offset);
    }

    @Override
    public DetallePedidoDTO obtenerDetallePedidoPorId(long id){
        try{
            log.info("Obteniendo detalle pedido por ID: {}", id);
            return jdbcTemplate.queryForObject("SELECT * FROM detalle_pedido WHERE id = ?", detallePedidoRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Error al ejecutar la consulta: {}", e.getMessage());
            throw new RecursoNoEncontradoException("DetallePedido", "id", id);
        }
    }

    @Override
    public DetallePedidoDTO crearDetallePedido(CreateDetallePedidoDTO detallePedidoDTO){

        Map <String, Object> fields = new LinkedHashMap<>();

        fields.put("id_pedido", detallePedidoDTO.getIdPedido());
        fields.put("id_producto_presentacion", detallePedidoDTO.getIdProductoPresentacion());
        fields.put("id_lote", detallePedidoDTO.getIdLote());
        fields.put("cantidad", detallePedidoDTO.getCantidad());
        fields.put("precio_unitario", detallePedidoDTO.getPrecioUnitario());
        fields.put("subtotal", detallePedidoDTO.getSubTotal());

        String sql = DynamicSqlBuilder.buildInsertSql("detalle_pedido", fields);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        log.info("Creando detalle pedido con datos: {}", fields);

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int i = 1;
            for (Object value : fields.values()) {
                ps.setObject(i++, value);
            }
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            log.error("Error al crear el detalle pedido, clave generada es nula");
            throw new RecursoNoEncontradoException("DetallePedidos", "filtro aplicado", null);
        }

        return obtenerDetallePedidoPorId(key.longValue());

    }

    @Override
    public void actualizarDetallePedido(long id, UpdateDetallePedidoDTO detallePedidoDTO){

        Map<String, Object> fields = new LinkedHashMap<>();

        if (detallePedidoDTO.getIdProductoPresentacion() != null) fields.put("id_producto_presentacion", detallePedidoDTO.getIdProductoPresentacion());
        if (detallePedidoDTO.getIdLote() != null) fields.put("id_lote", detallePedidoDTO.getIdLote());
        if (detallePedidoDTO.getCantidad() != null) fields.put("cantidad", detallePedidoDTO.getCantidad());
        if (detallePedidoDTO.getPrecioUnitario() != null) fields.put("precio_unitario", detallePedidoDTO.getPrecioUnitario());
        if (detallePedidoDTO.getSubTotal() != null) fields.put("subtotal", detallePedidoDTO.getSubTotal());

        if (fields.isEmpty()) {
            log.error("Error al actualizar el detalle pedido, no se proporcionaron campos para actualizar");
            return;
        }

        String sql = DynamicSqlBuilder.buildUpdateSql("detalle_pedido", fields, "id = ?");

        Object[] params = Stream.concat(fields.values().stream(), Stream.of(id)).toArray();

        log.info("Actualizando detalle pedido con datos: {}", fields);

        jdbcTemplate.update(sql, params);

    }


    @Override
    public void borrarDetallePedido(long id){
        if (!existeDetallePedido(id)) {
            log.warn("No se encontró el detalle pedido con ID: {}", id);
            throw new RecursoNoEncontradoException("DetallePedido", "ID", id);
        }
        String sql = DynamicSqlBuilder.buildDeleteSql("detalle_pedido", "id = ?");

        log.info("Borrando detalle pedido con ID: {}", id);

        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existeDetallePedido(long id){
        String sql = DynamicSqlBuilder.buildCountSql("detalle_pedido", "id = ?");
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

}
