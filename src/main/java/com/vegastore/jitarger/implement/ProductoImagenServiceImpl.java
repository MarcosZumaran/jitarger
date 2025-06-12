package com.vegastore.jitarger.implement;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

import com.vegastore.jitarger.dto.base.ProductoImagenDTO;
import com.vegastore.jitarger.dto.create.CreateProductoImagenDTO;
import com.vegastore.jitarger.dto.update.UpdateProductoImagenDTO;
import com.vegastore.jitarger.exception.RecursoNoEncontradoException;
import com.vegastore.jitarger.service.ProductoImagenService;
import com.vegastore.jitarger.util.DynamicSqlBuilder;

@Service
public class ProductoImagenServiceImpl implements ProductoImagenService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger log = LoggerFactory.getLogger(ProductoImagenServiceImpl.class);

    private final RowMapper<ProductoImagenDTO> rowMapper = (rs, rowNum) -> ProductoImagenDTO.builder()
            .id(rs.getLong("id"))
            .idProducto(rs.getLong("producto_id"))
            .url(rs.getString("url"))
            .tipo(rs.getString("tipo"))
            .build();

    private List<ProductoImagenDTO> ejecutarConsultaListaProductoImagen(String sql, Object... args) {
        try {
            log.info("Ejecutando consulta: {}", sql);
            return jdbcTemplate.query(sql, rowMapper, args);
        } catch (EmptyResultDataAccessException e) {
            log.error("Error al ejecutar la consulta: {}", e.getMessage());
            throw new RecursoNoEncontradoException("Imagenes de Productos", "filtro aplicado", null);
        }
    }

    private static final Set<String> TIPOS_VALIDOS = Set.of("PORTADA", "GALERIA");

    private void validarTipo(String tipo) {
        if (tipo == null || !TIPOS_VALIDOS.contains(tipo.toUpperCase())) {
            throw new IllegalArgumentException("Estado inválido: " + tipo);
        }
    }

    @Override
    public List<ProductoImagenDTO> obtenerImagenesPorProducto(long idProducto) {
        return ejecutarConsultaListaProductoImagen("SELECT * FROM producto_imagen WHERE producto_id = ?", idProducto);
    }

    @Override
    public List<ProductoImagenDTO> obtenerImagenesPorProductoYTipo(long idProducto, String tipo) {
        return ejecutarConsultaListaProductoImagen("SELECT * FROM producto_imagen WHERE producto_id = ? AND tipo = ?",
                idProducto, tipo);
    }

    @Override
    public List<ProductoImagenDTO> obtenerTodasLasImagenes() {
        return ejecutarConsultaListaProductoImagen("SELECT * FROM producto_imagen");
    }

    @Override
    public List<ProductoImagenDTO> obtenerImagenes(int pagina) {
        int offset = (pagina - 1) * 10; // Asumiendo que cada página tiene 10 imágenes
        return ejecutarConsultaListaProductoImagen("SELECT * FROM producto_imagen LIMIT 10 OFFSET ?", offset);
    }

    @Override
    public List<ProductoImagenDTO> obtenerTodasLasImagenesPorTipo(String tipo) {
        return ejecutarConsultaListaProductoImagen("SELECT * FROM producto_imagen WHERE tipo = ?", tipo);
    }

    @Override
    public List<ProductoImagenDTO> obtenerImagenesPorTipo(int pagina, String tipo) {
        int offset = (pagina - 1) * 10; // Asumiendo que cada página tiene 10 imágenes
        return ejecutarConsultaListaProductoImagen("SELECT * FROM producto_imagen WHERE tipo = ? LIMIT 10 OFFSET ?",
                tipo, offset);
    }

    @Override
    public ProductoImagenDTO obtenerImagenPorId(long id) {
        try {
            log.info("Obteniendo imagen por ID: {}", id);
            return jdbcTemplate.queryForObject("SELECT * FROM producto_imagen WHERE id = ?", rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Imagen con ID {} no encontrada", id);
            throw new RecursoNoEncontradoException("Imagen de Producto", "id", String.valueOf(id));
        }
    }

    @Override
    public ProductoImagenDTO crearImagen(CreateProductoImagenDTO imagenDTO) {

        Map<String, Object> fields = new LinkedHashMap<>();

        String tipo = imagenDTO.getTipo().toUpperCase();
        validarTipo(tipo);

        fields.put("producto_id", imagenDTO.getIdProducto());
        fields.put("url", imagenDTO.getUrl());
        fields.put("tipo", tipo);

        String sql = DynamicSqlBuilder.buildInsertSql("producto_imagen", fields);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        log.info("Creando imagen con datos: {}", fields);

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
            log.error("Error al crear la imagen, clave generada es nula");
            throw new RuntimeException("Error al crear la imagen, clave generada es nula");
        }

        log.info("Imagen creada con ID: {}", key.longValue());

        return obtenerImagenPorId(key.longValue());

    }

    @Override
    public void actualizarImagen(long id, UpdateProductoImagenDTO imagenDTO) {

        if (!existeImagen(id)) {
            log.error("Imagen con ID {} no encontrada", id);
            throw new RecursoNoEncontradoException("Imagen de Producto", "id", String.valueOf(id));
        }

        Map<String, Object> fields = new LinkedHashMap<>();

        if (imagenDTO.getUrl() != null)
            fields.put("url", imagenDTO.getUrl());

        if (imagenDTO.getTipo() != null) {
            String tipo = imagenDTO.getTipo().toUpperCase();
            validarTipo(tipo);
            fields.put("tipo", tipo);
        }

        if (fields.isEmpty()) {
            log.warn("No se proporcionaron campos para actualizar la imagen con ID: {}", id);
            return;
        }

        String sql = DynamicSqlBuilder.buildUpdateSql("producto_imagen", fields, "id = ?");

        Object[] params = Stream.concat(fields.values().stream(), Stream.of(id)).toArray();

        log.info("Actualizando imagen con ID: {} con datos: {}", id, fields);

        jdbcTemplate.update(sql, params);

    }

    @Override
    public void borrarImagen(long id) {
        String sql = DynamicSqlBuilder.buildDeleteSql("producto_imagen", "id = ?");
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existeImagen(long id) {
        String sql = DynamicSqlBuilder.buildCountSql("producto_imagen", "id = ?");
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public long contarImagenes() {
        String sql = DynamicSqlBuilder.buildCountSql("producto_imagen");
        Long count = jdbcTemplate.queryForObject(sql, Long.class);

        if (count == null) {
            log.warn("El conteo de imagenes devolvió null, devolviendo 0");
            return 0L;
        }

        return count;
    }

    @Override
    public long contarImagenesPorTipo(String tipo) {
        String sql = DynamicSqlBuilder.buildCountSql("producto_imagen", "tipo = ?");
        Long count = jdbcTemplate.queryForObject(sql, Long.class, tipo);

        if (count == null) {
            log.warn("El conteo de imagenes devolvió null, devolviendo 0");
            return 0L;
        }

        return count;
    }

    @Override
    public long contarImagenesPorProducto(long idProducto) {
        String sql = DynamicSqlBuilder.buildCountSql("producto_imagen", "producto_id = ?");
        Long count = jdbcTemplate.queryForObject(sql, Long.class, idProducto);

        if (count == null) {
            log.warn("El conteo de imagenes devolvió null, devolviendo 0");
            return 0L;
        }

        return count;
    }

}
