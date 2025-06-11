package com.vegastore.jitarger.implement;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.vegastore.jitarger.dto.base.CategoriaDTO;
import com.vegastore.jitarger.dto.create.CreateCategoriaDTO;
import com.vegastore.jitarger.dto.update.UpdateCategoriaDTO;
import com.vegastore.jitarger.exception.RecursoNoEncontradoException;
import com.vegastore.jitarger.service.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<CategoriaDTO> categoriaRowMapper = (rs, rowNum) -> CategoriaDTO.builder()
            .id(rs.getLong("id"))
            .nombre(rs.getString("nombre"))
            .descripcion(rs.getString("descripcion"))
            .fechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime())
            .fechaActualizacion(rs.getTimestamp("fecha_actualizacion").toLocalDateTime())
            .activa(rs.getBoolean("activa"))
            .build();

    @Override
    public List<CategoriaDTO> obtenerTodasLasCategorias() {
        String sql = "SELECT * FROM categoria";
        return jdbcTemplate.query(sql, categoriaRowMapper);
    }

    @Override
    public List<CategoriaDTO> obtenerCategorias(int pagina) {
        int pageSize = 10;
        int offset = (pagina - 1) * pageSize;
        String sql = "SELECT * FROM categoria LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, categoriaRowMapper, pageSize, offset);
    }

    @Override
    public List<CategoriaDTO> obtenerCategoriasPorNombre(String nombre) {
        String sql = "SELECT * FROM categoria WHERE nombre = ?";
        return jdbcTemplate.query(sql, categoriaRowMapper, nombre);
    }

    @Override
    public List<CategoriaDTO> obtenerCategoriasPorNombreParcial(String nombreParcial) {
        String sql = "SELECT * FROM categoria WHERE LOWER(nombre) LIKE ?";
        return jdbcTemplate.query(sql, categoriaRowMapper, "%" + nombreParcial.toLowerCase() + "%");
    }

    @Override
    public CategoriaDTO obtenerCategoriaPorId(long id) {
        String sql = "SELECT * FROM categoria WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, categoriaRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecursoNoEncontradoException("Categoría", "id", id);
        }
    }

    @Override
    public CategoriaDTO obtenerCategoriaPorProducto(long idProducto) {
        String sql = """
                    SELECT c.id, c.nombre, c.descripcion, c.fecha_registro, c.fecha_actualizacion, c.activa
                    FROM categoria c
                    JOIN producto_categoria pc ON c.id = pc.id_categoria
                    WHERE pc.id_producto = ?
                """;
        try {
            return jdbcTemplate.queryForObject(sql, categoriaRowMapper, idProducto);
        } catch (EmptyResultDataAccessException e) {
            throw new RecursoNoEncontradoException("Categoría no encontrada para producto", "id_producto", idProducto);
        }
    }

    @Override
    public CategoriaDTO crearCategoria(CreateCategoriaDTO dto) {
        String sql = "INSERT INTO categoria (nombre, descripcion, fecha_registro, fecha_actualizacion, activa) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        LocalDateTime ahora = LocalDateTime.now();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dto.getNombre());
            ps.setString(2, dto.getDescripcion());
            ps.setTimestamp(3, Timestamp.valueOf(ahora));
            ps.setTimestamp(4, Timestamp.valueOf(ahora));
            ps.setBoolean(5, true); // activa por defecto
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new RuntimeException("No se pudo obtener el ID generado para la categoría");
        }

        return obtenerCategoriaPorId(key.longValue());
    }

    @Override
    public void actualizarCategoria(long id, UpdateCategoriaDTO dto) {
        String sql = "UPDATE categoria SET nombre = ?, descripcion = ?, fecha_actualizacion = ? WHERE id = ?";
        int filas = jdbcTemplate.update(sql, dto.getNombre(), dto.getDescripcion(),
                Timestamp.valueOf(LocalDateTime.now()), id);

        if (filas == 0) {
            throw new RecursoNoEncontradoException("Categoria", "id", id);
        }
    }

    @Override
    public void borrarCategoria(long id) {
        String sql = "DELETE FROM categoria WHERE id = ?";
        int filas = jdbcTemplate.update(sql, id);

        if (filas == 0) {
            throw new RecursoNoEncontradoException("Categoria", "id", id);
        }
    }

    @Override
    public boolean existeCategoria(long id) {
        String sql = "SELECT COUNT(id) FROM categoria WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

}
