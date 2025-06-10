package com.vegastore.jitarger.implement;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.vegastore.jitarger.dto.base.CarritoDTO;
import com.vegastore.jitarger.dto.create.CreateCarritoDTO;
import com.vegastore.jitarger.dto.update.UpdateCarritoDTO;
import com.vegastore.jitarger.exception.RecursoNoEncontradoException;
import com.vegastore.jitarger.service.CarritoService;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Row mapper para mapear los resultados de la consulta a un objeto CarritoDTO
    private final RowMapper<CarritoDTO> carritoRowMapper = (rs, rowNum) -> {
        Timestamp fechaCambio = rs.getTimestamp("fecha_cambio_estado");
        return CarritoDTO.builder()
                .id(rs.getLong("id"))
                .idUsuario(rs.getLong("id_usuario"))
                .fechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime())
                .fechaCambioEstado(fechaCambio != null ? fechaCambio.toLocalDateTime() : null)
                .estado(rs.getString("estado"))
                .build();
    };

    private static final Set<String> ESTADOS_VALIDOS = Set.of("ACTIVO", "PROCESADO", "CANCELADO");

    private void validarEstado(String estado) {
        if (estado == null || !ESTADOS_VALIDOS.contains(estado.toUpperCase())) {
            throw new IllegalArgumentException("Estado inválido: " + estado);
        }
    }


    // Implementación de los métodos de la interfaz CarritoService

    @Override
    public CarritoDTO obtenerCarritoPorId(long id) {
        String sql = "SELECT * FROM carrito WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, carritoRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecursoNoEncontradoException("Carrito no encontrado con ID: " + id, sql, e);
        }
    }

    @Override
    public CarritoDTO obtenerCarritoPorUsuarioId(long id) {
        String sql = "SELECT * FROM carrito WHERE id_usuario = ?";
        try {
            return jdbcTemplate.queryForObject(sql, carritoRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecursoNoEncontradoException("Carrito no encontrado para el usuario con ID: " + id, sql, e);
        }
    }

    @Override
    public CarritoDTO crearCarrito(CreateCarritoDTO carritoDTO) {
        LocalDateTime fechaCreacion = LocalDateTime.now();

        String estado = carritoDTO.getEstado().toUpperCase();
        validarEstado(estado);

        String sql = "INSERT INTO carrito (id_usuario, fecha_creacion, estado) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, carritoDTO.getIdUsuario());
            ps.setTimestamp(2, Timestamp.valueOf(fechaCreacion));
            ps.setString(3, estado);
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new IllegalStateException("No se pudo obtener el ID generado para el carrito");
        }

        return obtenerCarritoPorId(key.longValue());
    }

    @Override
    public void actualizarCarrito(long id, UpdateCarritoDTO carritoDTO) {
        validarEstado(carritoDTO.getEstado().toUpperCase());

        String sql = "UPDATE carrito SET estado = ?, fecha_cambio_estado = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, carritoDTO.getEstado(), Timestamp.valueOf(LocalDateTime.now()), id);

        if (rowsAffected == 0) {
            throw new RecursoNoEncontradoException("Carrito no encontrado con ID: " + id, sql, rowsAffected);
        }
    }

    @Override
    public void borrarCarrito(long id) {
        String sql = "DELETE FROM carrito WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);

        if (rowsAffected == 0) {
            throw new RecursoNoEncontradoException("Carrito no encontrado con ID: " + id, sql, rowsAffected);
        }
    }

}
