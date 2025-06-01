package com.vegastore.jitarger.implement;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.vegastore.jitarger.dto.base.PedidoDTO;
import com.vegastore.jitarger.dto.create.CreatePedidoDTO;
import com.vegastore.jitarger.dto.update.UpdatePedidoDTO;
import com.vegastore.jitarger.exception.RecursoNoEncontradoException;
import com.vegastore.jitarger.service.PedidoService;

public class PedidoServiceImpl implements PedidoService{

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
            .fechaConfirmacion(rs.getTimestamp("fecha_confirmacion") != null ? 
                rs.getTimestamp("fecha_confirmacion").toLocalDateTime() : null)
            .fechaEntrega(rs.getTimestamp("fecha_entrega") != null ? 
                rs.getTimestamp("fecha_entrega").toLocalDateTime() : null)
            .cancelado(rs.getBoolean("cancelado"))
            .fechaCancelacion(rs.getTimestamp("fecha_cancelacion") != null ? 
                rs.getTimestamp("fecha_cancelacion").toLocalDateTime() : null)
            .build();
    
    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO> obtenerPedidos() {
        String sql = "SELECT * FROM pedido ORDER BY fecha_registro DESC";
        return jdbcTemplate.query(sql, pedidoRowMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO> obtenerPedidosPorUsuario(long idUsuario) {
        String sql = "SELECT * FROM pedido WHERE id_usuario = ? ORDER BY fecha_registro DESC";
        return jdbcTemplate.query(sql, pedidoRowMapper, idUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO> obtenerPedidosPorEstado(String estado) {
        String sql = "SELECT * FROM pedido WHERE estado = ? ORDER BY fecha_registro DESC";
        return jdbcTemplate.query(sql, pedidoRowMapper, estado);
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoDTO obtenerPedidoPorId(long id) {
        String sql = "SELECT * FROM pedido WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, pedidoRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecursoNoEncontradoException("Pedido no encontrado con ID: " + id, sql, e);
        }
    }

    @Override
    @Transactional
    public PedidoDTO crearPedido(CreatePedidoDTO pedidoDTO) {
        String sql = "INSERT INTO pedido (id_usuario, fecha_registro, estado, total, subtotal, descuento, impuestos, metodo_pago, direccion_entrega, fecha_confirmacion, fecha_entrega, cancelado, fecha_cancelacion) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, pedidoDTO.getIdUsuario());
            ps.setTimestamp(2, Timestamp.valueOf(pedidoDTO.getFechaRegistro()));
            ps.setString(3, pedidoDTO.getEstado());
            ps.setBigDecimal(4, pedidoDTO.getTotal());
            ps.setBigDecimal(5, pedidoDTO.getSubtotal());
            ps.setBigDecimal(6, pedidoDTO.getDescuento());
            ps.setBigDecimal(7, pedidoDTO.getImpuestos());
            ps.setString(8, pedidoDTO.getMetodoPago());
            ps.setString(9, pedidoDTO.getDireccionEntrega());
            ps.setTimestamp(10, pedidoDTO.getFechaConfirmacion() != null ? Timestamp.valueOf(pedidoDTO.getFechaConfirmacion()) : null);
            ps.setTimestamp(11, pedidoDTO.getFechaEntrega() != null ? Timestamp.valueOf(pedidoDTO.getFechaEntrega()) : null);
            ps.setBoolean(12, pedidoDTO.isCancelado());
            ps.setTimestamp(13, pedidoDTO.getFechaCancelacion() != null ? Timestamp.valueOf(pedidoDTO.getFechaCancelacion()) : null);
            return ps;
        }, keyHolder);
        
        Number key = keyHolder.getKey();
        if (key == null) {
            throw new RuntimeException("Error al crear el pedido, no se generó un ID.");
        }

        long id = Objects.requireNonNull(key).longValue();

        return obtenerPedidoPorId(id);
    }

    @Override
    @Transactional
    public void actualizarPedido(long id, UpdatePedidoDTO pedidoDTO) {
        if (!existePedido(id)) {
            throw new RecursoNoEncontradoException("Pedido no encontrado con ID: " + id, null, pedidoDTO);
        }

        String sql = "UPDATE pedido SET fecha_registro = ?, estado = ?, fecha_confirmacion = ?, fecha_entrega = ?, cancelado = ?, fecha_cancelacion = ? WHERE id = ?";
        jdbcTemplate.update(sql, 
            Timestamp.valueOf(pedidoDTO.getFechaRegistro()), 
            pedidoDTO.getEstado(), 
            pedidoDTO.getFechaConfirmacion() != null ? Timestamp.valueOf(pedidoDTO.getFechaConfirmacion()) : null,
            pedidoDTO.getFechaEntrega() != null ? Timestamp.valueOf(pedidoDTO.getFechaEntrega()) : null,
            pedidoDTO.isCancelado(),
            pedidoDTO.getFechaCancelacion() != null ? Timestamp.valueOf(pedidoDTO.getFechaCancelacion()) : null,
            id);
    }

    @Override
    @Transactional
    public void cancelarPedido(long id, String comentario) {
        if (!existePedido(id)) {
            throw new RecursoNoEncontradoException("Pedido no encontrado con ID: " + id, null, null);
        }

        String sql = "UPDATE pedido SET estado = 'cancelado', cancelado = true, fecha_cancelacion = ? WHERE id = ?";
        jdbcTemplate.update(sql, Timestamp.valueOf(java.time.LocalDateTime.now()), id);
    }

    @Override
    @Transactional
    public void actualizarEstadoPedido(long id, String nuevoEstado, String comentario) {
        if (!existePedido(id)) {
            throw new RecursoNoEncontradoException("Pedido no encontrado con ID: " + id, null, null);
        }

        String sql = "UPDATE pedido SET estado = ? WHERE id = ?";
        jdbcTemplate.update(sql, nuevoEstado, id);
    }

    @Override
    @Transactional
    public void borrarPedido(long id) {
        if (!existePedido(id)) {
            throw new RecursoNoEncontradoException("Pedido no encontrado con ID: " + id, null, null);
        }
        
        String sql = "DELETE FROM pedido WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePedido(long id) {
        String sql = "SELECT COUNT(*) FROM pedido WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

}
