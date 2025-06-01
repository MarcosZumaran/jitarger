package com.vegastore.jitarger.implement;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.vegastore.jitarger.dto.base.HistorialPedidoDTO;
import com.vegastore.jitarger.dto.create.CreateHistorialPedidoDTO;
import com.vegastore.jitarger.exception.RecursoNoEncontradoException;
import com.vegastore.jitarger.service.HistorialPedidoService;

@Service
public class HistorialPedidoServiceImpl implements HistorialPedidoService{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<HistorialPedidoDTO> historialPedidoRowMapper = (rs, rowNum) -> HistorialPedidoDTO.builder()
            .id(rs.getLong("id"))
            .estadoAnterior("estado_anterior")
            .estadoNuevo("estado_nuevo")
            .fechaCambio(rs.getTimestamp("fecha_cambio").toLocalDateTime())
            .comentario(rs.getString("comentario"))
            .build();

    
    @Override
    public List<HistorialPedidoDTO> obtenerHistorialPedidoPorUsuario(long idUsuario) {
        String sql = "SELECT * FROM historial_pedido WHERE id_usuario = ? ORDER BY fecha_cambio DESC";
        return jdbcTemplate.query(sql, historialPedidoRowMapper, idUsuario);
    }

    @Override
    public List<HistorialPedidoDTO> obtenerHistorialPedidoPorPedido(long idPedido) {
        String sql = "SELECT * FROM historial_pedido WHERE id_pedido = ? ORDER BY fecha_cambio DESC";
        return jdbcTemplate.query(sql, historialPedidoRowMapper, idPedido);
    }

    @Override
    public HistorialPedidoDTO obtenerHistorialPedidoPorId(long id) {
        String sql = "SELECT * FROM historial_pedido WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, historialPedidoRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecursoNoEncontradoException("Historial de pedido no encontrado con ID: " + id, sql, e);
        }
    }

    @Override
    public HistorialPedidoDTO crearHistorialPedido(CreateHistorialPedidoDTO historialPedidoDTO) {
        String sql = "INSERT INTO historial_pedido (id_pedido, estado_anterior, estado_nuevo, fecha_cambio, comentario) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, historialPedidoDTO.getIdPedido());
            ps.setString(2, historialPedidoDTO.getEstadoAnterior());
            ps.setString(3, historialPedidoDTO.getEstadoNuevo());
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(5, historialPedidoDTO.getComentario());
            return ps;
        }, keyHolder);

        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return HistorialPedidoDTO.builder()
                .id(id)
                .idPedido(historialPedidoDTO.getIdPedido())
                .estadoAnterior(historialPedidoDTO.getEstadoAnterior())
                .estadoNuevo(historialPedidoDTO.getEstadoNuevo())
                .fechaCambio(LocalDateTime.now())
                .comentario(historialPedidoDTO.getComentario())
                .build();
    }

    @Override
    public void borrarHistorialPedido(long id) {
        if (!existeHistorialPedido(id)) {
            throw new RecursoNoEncontradoException("Historial de pedido", "id", id);
        }
        
        String sql = "DELETE FROM historial_pedido WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existeHistorialPedido(long id) {
        String sql = "SELECT COUNT(*) FROM historial_pedido WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
    

}
