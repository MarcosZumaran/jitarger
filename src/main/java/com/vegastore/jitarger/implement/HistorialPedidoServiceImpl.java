package com.vegastore.jitarger.implement;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;  

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
public class HistorialPedidoServiceImpl implements HistorialPedidoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<HistorialPedidoDTO> historialPedidoRowMapper = (rs, rowNum) -> HistorialPedidoDTO.builder()
            .id(rs.getLong("id"))
            .idPedido(rs.getLong("id_pedido"))
            .estadoAnterior(rs.getString("estado_anterior"))
            .estadoNuevo(rs.getString("estado_nuevo"))
            .fechaCambio(rs.getTimestamp("fecha_cambio").toLocalDateTime())
            .comentario(rs.getString("comentario"))
            .build();

    @Override
    public List<HistorialPedidoDTO> obtenerHistorialPedidoPorUsuario(int pagina, long idUsuario) {
        int limit = 10;
        int offset = (pagina - 1) * limit;

        String sql = """
                    SELECT hp.*
                    FROM historial_pedido hp
                    JOIN pedido p ON hp.id_pedido = p.id
                    WHERE p.id_usuario = ?
                    ORDER BY hp.fecha_cambio DESC
                    LIMIT ? OFFSET ?
                """;

        return jdbcTemplate.query(sql, historialPedidoRowMapper, idUsuario, limit, offset);
    }

    @Override
    public List<HistorialPedidoDTO> obtenerHistorialPedidoPorPedido(int pagina, long idPedido) {
        int limit = 10;
        int offset = (pagina - 1) * limit;

        String sql = """
                    SELECT * FROM historial_pedido
                    WHERE id_pedido = ?
                    ORDER BY fecha_cambio DESC
                    LIMIT ? OFFSET ?
                """;

        return jdbcTemplate.query(sql, historialPedidoRowMapper, idPedido, limit, offset);
    }

    @Override
    public HistorialPedidoDTO obtenerHistorialPedidoPorId(long id) {
        String sql = "SELECT * FROM historial_pedido WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, historialPedidoRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecursoNoEncontradoException("Historial del pedido", "id", id);
        }
    }

    @Override
    public HistorialPedidoDTO crearHistorialPedido(CreateHistorialPedidoDTO dto) {
        String sql = """
                    INSERT INTO historial_pedido (id_pedido, estado_anterior, estado_nuevo, fecha_cambio, comentario)
                    VALUES (?, ?, ?, ?, ?)
                    RETURNING id
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, dto.getIdPedido());
            ps.setString(2, dto.getEstadoAnterior());
            ps.setString(3, dto.getEstadoNuevo());
            ps.setTimestamp(4, Timestamp.valueOf(dto.getFechaCambio()));
            ps.setString(5, dto.getComentario());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        
        if (key == null) {
            throw new RuntimeException("Error al crear el historial del pedido");
        }
        
        return obtenerHistorialPedidoPorId(key.longValue());
    }

    @Override
    public void borrarHistorialPedido(long id) {
        String sql = "DELETE FROM historial_pedido WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows == 0) {
            throw new RecursoNoEncontradoException("Historial del pedido", "id", id);
        }
    }

    @Override
    public boolean existeHistorialPedido(long id) {
        String sql = "SELECT COUNT(*) FROM historial_pedido WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

}
