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
import org.springframework.transaction.annotation.Transactional;

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
            .build();

    // Aquí irían las implementaciones de los métodos de la interfaz CategoriaService
    // Por ejemplo, obtener categorías, crear una nueva categoría, etc.

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaDTO> obtenerCategorias(){
        String sql = "SELECT * FROM categoria ORDER BY nombre";
        // Utilizamos jdbcTemplate para ejecutar la consulta y mapear los resultados a una lista de CategoriaDTO
        // El RowMapper se encarga de convertir cada fila del resultado en un objeto CategoriaDTO
        // Retornamos la lista de categorías
        // Si no hay categorías, se retornará una lista vacía
        // Si hay categorías, se retornará una lista con los objetos CategoriaDTO correspondientes
        return jdbcTemplate.query(sql, categoriaRowMapper);

    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaDTO obtenerCategoriaPorId(long id){
        // Sentencia SQL para obtener una categoría por su ID
        String sql = "SELECT * FROM categoria WHERE id = ?";

        try {
            // Ejecutamos la consulta y mapeamos el resultado a un objeto CategoriaDTO
            return jdbcTemplate.queryForObject(sql, categoriaRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            // Si no se encuentra la categoría, lanzamos una excepción personalizada
            throw new RecursoNoEncontradoException("No se encontro la categoria con ID: " + id, sql, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaDTO obtenerCategoriaPorProducto(long idProducto) {
        // Sentencia SQL para obtener una categoría por el ID de un producto
        String sql = "SELECT c.* FROM categoria c " +
                    "JOIN producto_categoria pc ON c.id = pc.categoria_id " +
                    "WHERE pc.producto_id = ?";

        try {
            // Ejecutamos la consulta y mapeamos el resultado a un objeto CategoriaDTO
            return jdbcTemplate.queryForObject(sql, categoriaRowMapper, idProducto);
        } catch (EmptyResultDataAccessException e) {
            // Si no se encuentra la categoría, lanzamos una excepción personalizada
            throw new RecursoNoEncontradoException("No se encontro la categoria para el producto con ID: " + idProducto, sql, e);
        }
    }

    @Override
    @Transactional
    public CategoriaDTO crearCategoria(CreateCategoriaDTO categoria) {
        String sql = "INSERT INTO categoria (nombre, descripcion, fecha_registro) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            return ps;
        }, keyHolder);
        
        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        
        return CategoriaDTO.builder()
                .id(id)
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .fechaRegistro(LocalDateTime.now())
                .build();
    }

    @Override
    @Transactional
    public void actualizarCategoria(long id, UpdateCategoriaDTO categoria) {
        if (!existeCategoria(id)) {
            throw new RecursoNoEncontradoException("Categoría", "id", id);
        }
        
        String sql = "UPDATE categoria SET nombre = ?, descripcion = ? WHERE id = ?";
        jdbcTemplate.update(sql, categoria.getNombre(), categoria.getDescripcion(), id);
    }

    @Override
    @Transactional
    public void borrarCategoria(long id) {
        if (!existeCategoria(id)) {
            throw new RecursoNoEncontradoException("Categoría", "id", id);
        }
        
        String sql = "DELETE FROM categoria WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeCategoria(long id) {
        String sql = "SELECT COUNT(id) FROM categoria WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public int contarCategorias() {
        String sql = "SELECT COUNT(id) FROM categoria";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null ? count : 0;
    }
    
}
