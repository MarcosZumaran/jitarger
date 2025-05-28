package com.vegastore.jitarger.implement;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vegastore.jitarger.dto.base.DetallePedidoDTO;
import com.vegastore.jitarger.dto.create.CreateDetallePedidoDTO;
import com.vegastore.jitarger.dto.update.UpdateDetallePedidoDTO;
import com.vegastore.jitarger.exception.RecursoNoEncontradoException;
import com.vegastore.jitarger.service.DetallePedidoService;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService{

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
    
    // Implementación de los métodos del servicio
    @Override
    @Transactional(readOnly = true)
    public List<DetallePedidoDTO> obtenerDetallesPedidoPorPedido(long pedidoId){
        String sql = "SELECT * FROM detalle_pedido WHERE id_pedido = ?";
        return jdbcTemplate.query(sql, detallePedidoRowMapper, pedidoId);
    }

    @Override
    public DetallePedidoDTO obtenerDetallePedidoPorId(long id){
        try{
            String sql = "SELECT * FROM detalle_pedido WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, detallePedidoRowMapper, id);
        } catch(EmptyResultDataAccessException e){
            throw new RecursoNoEncontradoException("Detalle de pedido", "id", id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DetallePedidoDTO crearDetallePedido(CreateDetallePedidoDTO detallePedidoDTO){
        String sql = "INSERT INTO detalle_pedido(id_pedido, " + 
                    "id_producto_presentacion, " + 
                    "id_lote, " + 
                    "cantidad, " + 
                    "precio_unitario, " + 
                    "subtotal, " + 
                    "nombre_producto, " + 
                    "unidad_medida_presentacion)" + 
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, detallePedidoDTO.getIdPedido());
            ps.setLong(2, detallePedidoDTO.getIdProductoPresentacion());
            ps.setLong(3, detallePedidoDTO.getIdLote());
            ps.setBigDecimal(4, detallePedidoDTO.getCantidad());
            ps.setBigDecimal(5, detallePedidoDTO.getPrecioUnitario());
            ps.setBigDecimal(6, detallePedidoDTO.getSubTotal());
            ps.setString(7, detallePedidoDTO.getNombreProducto());
            ps.setString(8, detallePedidoDTO.getUnidadmedidaPresentacion());
            return ps;
        }, keyHolder);

        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return DetallePedidoDTO.builder()
                .id(id)
                .idPedido(detallePedidoDTO.getIdPedido())
                .idProductoPresentacion(detallePedidoDTO.getIdProductoPresentacion())
                .idLote(detallePedidoDTO.getIdLote())
                .cantidad(detallePedidoDTO.getCantidad())
                .precioUnitario(detallePedidoDTO.getPrecioUnitario())
                .subTotal(detallePedidoDTO.getSubTotal())
                .nombreProducto(detallePedidoDTO.getNombreProducto())
                .unidadmedidaPresentacion(detallePedidoDTO.getUnidadmedidaPresentacion())
                .build();
    
    }

    @Override
    @Transactional
    public void actualizarDetallePedido(long id, UpdateDetallePedidoDTO detallePedidoDTO){
        if (!existeDetallePedido(id)) {
            throw new RecursoNoEncontradoException("Detalle de pedido", "id", id);
        }
        String sql = "UPDATE detalle_pedido SET " +
                    "id_producto_presentacion = ?, " +
                    "id_lote = ?, " +
                    "cantidad = ?, " +
                    "precio_unitario = ?, " +
                    "subtotal = ?, " +
                    "nombre_producto = ?, " +
                    "unidad_medida_presentacion = ? " +
                    "WHERE id = ?";
        jdbcTemplate.update(sql, detallePedidoDTO.getIdProductoPresentacion(),
                            detallePedidoDTO.getIdLote(),
                            detallePedidoDTO.getCantidad(),
                            detallePedidoDTO.getPrecioUnitario(),
                            detallePedidoDTO.getSubTotal(),
                            detallePedidoDTO.getNombreProducto(),
                            detallePedidoDTO.getUnidadmedidaPresentacion(),
                            id);
    }

    @Override
    @Transactional
    public void borrarDetallePedido(long id){
        if (!existeDetallePedido(id)) {
            throw new RecursoNoEncontradoException("Detalle de pedido", "id", id);
        }
        String sql = "DELETE FROM detalle_pedido WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeDetallePedido(long id) {
        String sql = "SELECT COUNT(*) FROM detalle_pedido WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

}
