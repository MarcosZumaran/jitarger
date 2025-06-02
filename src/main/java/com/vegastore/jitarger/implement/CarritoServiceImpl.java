package com.vegastore.jitarger.implement;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.vegastore.jitarger.dto.base.CarritoDTO;
import com.vegastore.jitarger.dto.create.CreateCarritoDTO;
import com.vegastore.jitarger.exception.RecursoDuplicadoException;
import com.vegastore.jitarger.exception.RecursoNoEncontradoException;
import com.vegastore.jitarger.service.CarritoService;

@Service
public class CarritoServiceImpl implements CarritoService{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Row mapper para mapear los resultados de la consulta a un objeto CarritoDTO
    private final RowMapper<CarritoDTO> carritoRowMapper = (rs, rowNum) -> CarritoDTO.builder()
    .id(rs.getLong("id"))
    .idUsuario(rs.getLong("id_usuario"))
    .fechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime())
    .build(); 

    // Implementación de los métodos de la interfaz CarritoService

    @Override
    public CarritoDTO obtenerCarritoPorId(long id) {
        String sql = "SELECT * FROM carrito WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, carritoRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecursoNoEncontradoException("No se encontro el carrito con ID: " + id, sql, e);
        }
    }

    @Override
    public CarritoDTO obtenerCarritoPorUsuarioId(long id) {
        String sql = "SELECT * FROM carrito WHERE id_usuario = ?";
        try {
            return jdbcTemplate.queryForObject(sql, carritoRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecursoNoEncontradoException("No se encontro el carrito con ID de usuario: " + id, sql, e);
        }
    }

    @Override
    public long crearCarrito(CreateCarritoDTO carrito) {
        // Si no se proporciona fecha de creación, usar la fecha actual
        final LocalDateTime fechaCreacionFinal = carrito.getFechaCreacion() != null ? carrito.getFechaCreacion()
                : LocalDateTime.now();

        // Insertar el carrito y obtener el ID generado
        String sql = "INSERT INTO carrito (id_usuario, fecha_creacion) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, carrito.getIdUsuario());
            ps.setTimestamp(2, Timestamp.valueOf(fechaCreacionFinal));
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new RuntimeException("No se pudo obtener el ID generado para el carrito");
        }
        return key.intValue();
    }

    @Override
    public void borrarCarrito(long id){
        String sql = "DELETE FROM carrito WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        if (rowsAffected == 0) {
            throw new RecursoNoEncontradoException("No se encontro el carrito con ID: " + id, sql, rowsAffected);
        }
    }

    @Override
    public boolean existeCarritoParaUsuario(long idUsuario) {
        String sql = "SELECT COUNT(id) FROM carrito WHERE id_usuario = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, idUsuario);
        return count != null && count > 0;
    }
    
}
