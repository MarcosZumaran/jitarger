package com.vegastore.jitarger.implement;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

import com.vegastore.jitarger.dto.base.ProductoDTO;
import com.vegastore.jitarger.dto.create.CreateProductoDTO;
import com.vegastore.jitarger.dto.update.UpdateProductoDTO;
import com.vegastore.jitarger.exception.RecursoNoEncontradoException;
import com.vegastore.jitarger.service.ProductoService;
import com.vegastore.jitarger.util.DynamicSqlBuilder;

@Service
public class ProductoServiceImpl implements ProductoService {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);

    private final RowMapper<ProductoDTO> rowMapper = (rs, rowNum) -> ProductoDTO.builder()
            .id(rs.getLong("id"))
            .nombre(rs.getString("nombre"))
            .descripcion(rs.getString("descripcion"))
            .idCategoria(rs.getLong("id_categoria"))
            .idSubCategoria(rs.getLong("id_subcategoria"))
            .estado(rs.getString("estado"))
            .tipoPrenda(rs.getString("tipo_prenda"))
            .marca(rs.getString("marca"))
            .talla(rs.getString("talla"))
            .color(rs.getString("color"))
            .genero(rs.getString("genero"))
            .material(rs.getString("material"))
            .temporada(rs.getString("temporada"))
            .fechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime())
            .fechaActualizacion(rs.getTimestamp("fecha_actualizacion").toLocalDateTime())
            .build();

    private List<ProductoDTO> ejecutarConsultaListaProductos(String sql, Object... parametros) {
        try {
            log.info("Ejecutando consulta: {}", sql);
            return jdbcTemplate.query(sql, rowMapper, parametros);
        } catch (EmptyResultDataAccessException e) {
            log.error("Error al consultar la base de datos", e);
            throw new RecursoNoEncontradoException("Productos", "filtro aplicado", null);
        }
    }

    @Override
    public List<ProductoDTO> obtenerTodosLosProductos() {
        return ejecutarConsultaListaProductos("SELECT * FROM producto");
    }

    @Override
    public List<ProductoDTO> obtenerProductos(int pagina) {
        int offset = (pagina - 1) * 10; // Asumiendo que cada página tiene 10 productos
        return ejecutarConsultaListaProductos("SELECT * FROM producto LIMIT 10 OFFSET ?", offset);
    }

    @Override
    public List<ProductoDTO> obtenerTodosLosProductosPorCategoria(long idCategoria) {
        return ejecutarConsultaListaProductos("SELECT * FROM producto WHERE id_categoria = ?", idCategoria);
    }

    @Override
    public List<ProductoDTO> obtenerTodosLosProductosPorSubcategoria(long idSubcategoria) {
        return ejecutarConsultaListaProductos("SELECT * FROM producto WHERE id_subcategoria = ?", idSubcategoria);
    }

    @Override
    public List<ProductoDTO> obtenerTodosLosProductosPorMarca(String marca) {
        return ejecutarConsultaListaProductos("SELECT * FROM producto WHERE marca = ?", marca);
    }

    @Override
    public List<ProductoDTO> obtenerTodosLosProductosPorTalla(String talla) {
        return ejecutarConsultaListaProductos("SELECT * FROM producto WHERE talla = ?", talla);
    }

    @Override
    public List<ProductoDTO> obtenerTodosLosProductosPorColor(String color) {
        return ejecutarConsultaListaProductos("SELECT * FROM producto WHERE color = ?", color);
    }

    @Override
    public List<ProductoDTO> obtenerTodosLosProductosPorGenero(String genero) {
        return ejecutarConsultaListaProductos("SELECT * FROM producto WHERE genero = ?", genero);
    }

    @Override
    public List<ProductoDTO> obtenerTodosLosProductosPorEstado(String estado) {
        return ejecutarConsultaListaProductos("SELECT * FROM producto WHERE estado = ?", estado);
    }

    @Override
    public List<ProductoDTO> obtenerTodosLosProductosPorNombre(String nombre) {
        return ejecutarConsultaListaProductos("SELECT * FROM producto WHERE nombre = ?", nombre);
    }

    @Override
    public List<ProductoDTO> obetnerTodosLosProductosPorNombreParcial(String nombre) {
        return ejecutarConsultaListaProductos("SELECT * FROM producto WHERE nombre LIKE ?", "%" + nombre + "%");
    }

    @Override
    public List<ProductoDTO> obtenerProductosPorCategoria(int pagina, long idCategoria) {
        int offset = (pagina - 1) * 10; // Asumiendo que cada página tiene 10 productos
        return ejecutarConsultaListaProductos("SELECT * FROM producto WHERE id_categoria = ? LIMIT 10 OFFSET ?", idCategoria, offset);
    }

    @Override
    public List<ProductoDTO> obtenerProductosPorSubcategoria(int pagina, long idSubcategoria) {
        int offset = (pagina - 1) * 10; // Asumiendo que cada página tiene 10 productos
        return ejecutarConsultaListaProductos("SELECT * FROM producto WHERE id_subcategoria = ? LIMIT 10 OFFSET ?", idSubcategoria, offset);
    }

    @Override
    public List<ProductoDTO> obtenerProductosPorMarca(int pagina, String marca) {
        int offset = (pagina - 1) * 10; // Asumiendo que cada página tiene 10 productos
        return ejecutarConsultaListaProductos("SELECT * FROM producto WHERE marca = ? LIMIT 10 OFFSET ?", marca, offset);
    }

    @Override
    public List<ProductoDTO> obtenerProductosPorTalla(int pagina, String talla) {
        int offset = (pagina - 1) * 10; // Asumiendo que cada página tiene 10 productos
        return ejecutarConsultaListaProductos("SELECT * FROM producto WHERE talla = ? LIMIT 10 OFFSET ?", talla, offset);
    }

    @Override
    public List<ProductoDTO> obtenerProductosPorColor(int pagina, String color) {
        int offset = (pagina - 1) * 10; // Asumiendo que cada página tiene 10 productos
        return ejecutarConsultaListaProductos("SELECT * FROM producto WHERE color = ? LIMIT 10 OFFSET ?", color, offset);
    }

    @Override
    public List<ProductoDTO> obtenerProductosPorGenero(int pagina, String genero) {
        int offset = (pagina - 1) * 10; // Asumiendo que cada página tiene 10 productos
        return ejecutarConsultaListaProductos("SELECT * FROM producto WHERE genero = ? LIMIT 10 OFFSET ?", genero, offset);
    }

    @Override
    public List<ProductoDTO> obtenerProductosPorEstado(int pagina, String estado) {
        int offset = (pagina - 1) * 10; // Asumiendo que cada página tiene 10 productos
        return ejecutarConsultaListaProductos("SELECT * FROM producto WHERE estado = ? LIMIT 10 OFFSET ?", estado, offset);
    }

    @Override
    public List<ProductoDTO> obtenerProductosPorNombre(int pagina, String nombre) {
        int offset = (pagina - 1) * 10; // Asumiendo que cada página tiene 10 productos
        return ejecutarConsultaListaProductos("SELECT * FROM producto WHERE nombre = ? LIMIT 10 OFFSET ?", nombre, offset);
    }

    @Override
    public List<ProductoDTO> obtenerProductosPorNombreParcial(int pagina, String nombre) {
        int offset = (pagina - 1) * 10; // Asumiendo que cada página tiene 10 productos
        return ejecutarConsultaListaProductos("SELECT * FROM producto WHERE nombre LIKE ? LIMIT 10 OFFSET ?", "%" + nombre + "%", offset);
    }

    @Override
    public ProductoDTO obtenerProductoPorId(long id) {
        try{
            log.info("Obteniendo producto por ID: {}", id);
            return jdbcTemplate.queryForObject("SELECT * FROM producto WHERE id = ?", rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Error al ejecutar la consulta: {}", e.getMessage());
            throw new RecursoNoEncontradoException("Productos", "filtro aplicado", String.valueOf(id));
        }
    }

    @Override
    public ProductoDTO crearProducto(CreateProductoDTO productoDTO) {
        
        Map<String, Object> fields = new LinkedHashMap<>();

        fields.put("id_categoria", productoDTO.getIdCategoria());
        fields.put("id_subcategoria", productoDTO.getIdSubCategoria());
        fields.put("nombre", productoDTO.getNombre());
        fields.put("descripcion", productoDTO.getDescripcion());
        fields.put("estado", productoDTO.getEstado());
        fields.put("tipo_prenda", productoDTO.getTipoPrenda());
        fields.put("marca", productoDTO.getMarca());
        fields.put("talla", productoDTO.getTalla());
        fields.put("color", productoDTO.getColor());
        fields.put("genero", productoDTO.getGenero());
        fields.put("material", productoDTO.getMaterial());
        fields.put("temporada", productoDTO.getTemporada());
        fields.put("fecha_creacion", Timestamp.valueOf(LocalDateTime.now()));
        fields.put("fecha_actualizacion", Timestamp.valueOf(LocalDateTime.now()));

        String sql = DynamicSqlBuilder.buildInsertSql("producto", fields);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        log.info("Creando producto con datos: {}", fields);

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
            log.error("Error al crear el producto, clave generada es nula");
            throw new RuntimeException("Error al crear el producto, clave generada es nula");
        }

        return obtenerProductoPorId(key.longValue());

    }

    @Override
    public void actualizarProducto(long id, UpdateProductoDTO productoDTO) {
        if(!existeProducto(id)){
            throw new RecursoNoEncontradoException("Productos", "filtro aplicado", String.valueOf(id));
        }

        Map<String, Object> fields = new LinkedHashMap<>();

        if (productoDTO.getIdCategoria() != null)
            fields.put("id_categoria", productoDTO.getIdCategoria());

        if (productoDTO.getIdSubCategoria() != null)
            fields.put("id_subcategoria", productoDTO.getIdSubCategoria());

        if (productoDTO.getNombre() != null)
            fields.put("nombre", productoDTO.getNombre());

        if (productoDTO.getDescripcion() != null)
            fields.put("descripcion", productoDTO.getDescripcion());

        if (productoDTO.getEstado() != null)
            fields.put("estado", productoDTO.getEstado());

        if (productoDTO.getTipoPrenda() != null)
            fields.put("tipo_prenda", productoDTO.getTipoPrenda());

        if (productoDTO.getMarca() != null)
            fields.put("marca", productoDTO.getMarca());

        if (productoDTO.getTalla() != null)
            fields.put("talla", productoDTO.getTalla());

        if (productoDTO.getColor() != null)
            fields.put("color", productoDTO.getColor());

        if (productoDTO.getGenero() != null)
            fields.put("genero", productoDTO.getGenero());

        if (productoDTO.getMaterial() != null)
            fields.put("material", productoDTO.getMaterial());

        if (productoDTO.getTemporada() != null)
            fields.put("temporada", productoDTO.getTemporada());

        if (fields.isEmpty()) {
            log.warn("No se proporcionaron campos para actualizar el producto con ID: {}", id);
            return;
        }

        fields.put("fecha_actualizacion", Timestamp.valueOf(LocalDateTime.now()));

        String sql = DynamicSqlBuilder.buildUpdateSql("producto", fields, "id = ?");

        log.info("Actualizando producto con ID: {} con datos: {}", id, fields);

        Object[] params = Stream.concat(fields.values().stream(), Stream.of(id)).toArray();

        jdbcTemplate.update(sql, params);
    }

    @Override
    public void borrarProducto(long id) {

        if(!existeProducto(id)){
            log.error("Producto con ID {} no encontrado", id);
            throw new RecursoNoEncontradoException("Productos", "filtro aplicado", String.valueOf(id));
        }

        log.info("Borrando producto con ID: {}", id);

        String sql = DynamicSqlBuilder.buildDeleteSql("producto", "id = ?");
        jdbcTemplate.update(sql, id);
    }
    
    @Override
    public boolean existeProducto(long id) {
        String sql = DynamicSqlBuilder.buildCountSql("producto", "id = ?");
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

}
