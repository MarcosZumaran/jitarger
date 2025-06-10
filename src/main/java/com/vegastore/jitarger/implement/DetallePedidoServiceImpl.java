package com.vegastore.jitarger.implement;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

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

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
    public List<DetallePedidoDTO> obtenerDetallesPedidoPorPedido(int pagina, long pedidoId) {
        int limite = 10;
        int offset = (pagina - 1) * limite;

        String sql = """
                    SELECT * FROM detalle_pedido
                    WHERE id_pedido = ?
                    LIMIT ? OFFSET ?
                """;

        return jdbcTemplate.query(sql, detallePedidoRowMapper, pedidoId, limite, offset);
    }

    @Override
    public DetallePedidoDTO obtenerDetallePedidoPorId(long id) {
        String sql = "SELECT * FROM detalle_pedido WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, detallePedidoRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecursoNoEncontradoException("DetallePedido no encontrado", "id", id);
        }
    }

    @Override
    public DetallePedidoDTO crearDetallePedido(CreateDetallePedidoDTO dto) {
        String sql = """
                    INSERT INTO detalle_pedido (
                        id_pedido, id_producto_presentacion, id_lote, cantidad,
                        precio_unitario, subtotal, nombre_producto, unidad_medida_presentacion
                    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, dto.getIdPedido());
            ps.setLong(2, dto.getIdProductoPresentacion());
            ps.setLong(3, dto.getIdLote());
            ps.setBigDecimal(4, dto.getCantidad());
            ps.setBigDecimal(5, dto.getPrecioUnitario());
            ps.setBigDecimal(6, dto.getSubTotal());
            ps.setString(7, dto.getNombreProducto());
            ps.setString(8, dto.getUnidadmedidaPresentacion());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new RuntimeException("No se pudo obtener el ID del detalle del pedido creado");
        }

        return obtenerDetallePedidoPorId(key.longValue());
    }

    @Override
    public void actualizarDetallePedido(long id, UpdateDetallePedidoDTO dto) {

        if (!existeDetallePedido(id)) {
            throw new RecursoNoEncontradoException("No se encontró detalle del pedido para actualizar", "id", id);
        }

        String sql = """
                    UPDATE detalle_pedido SET
                        id_producto_presentacion = ?, id_lote = ?, cantidad = ?,
                        precio_unitario = ?, subtotal = ?, nombre_producto = ?, unidad_medida_presentacion = ?
                    WHERE id = ?
                """;

        jdbcTemplate.update(sql,
                dto.getIdProductoPresentacion(),
                dto.getIdLote(),
                dto.getCantidad(),
                dto.getPrecioUnitario(),
                dto.getSubTotal(),
                dto.getNombreProducto(),
                dto.getUnidadmedidaPresentacion(),
                id);
    }

    @Override
    public void borrarDetallePedido(long id) {
        String sql = "DELETE FROM detalle_pedido WHERE id = ?";
        int deleted = jdbcTemplate.update(sql, id);
        if (deleted == 0) {
            throw new RecursoNoEncontradoException("No se encontró detalle del pedido para eliminar", "id", id);
        }
    }

    @Override
    public boolean existeDetallePedido(long id) {
        String sql = "SELECT COUNT(id) FROM detalle_pedido WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

}
