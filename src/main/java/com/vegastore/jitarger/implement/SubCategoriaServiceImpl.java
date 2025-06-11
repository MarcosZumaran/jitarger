package com.vegastore.jitarger.implement;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.vegastore.jitarger.dto.base.SubCategoriaDTO;
import com.vegastore.jitarger.dto.create.CreateSubCategoriaDTO;
import com.vegastore.jitarger.dto.update.UpdateSubCategoriaDTO;
import com.vegastore.jitarger.exception.RecursoNoEncontradoException;
import com.vegastore.jitarger.service.SubCategoriaService;
import com.vegastore.jitarger.util.DynamicSqlBuilder;

@Service
public class SubCategoriaServiceImpl implements SubCategoriaService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger log = LoggerFactory.getLogger(SubCategoriaServiceImpl.class);

    private final RowMapper<SubCategoriaDTO> rowMapper = (rs, rowNum) -> SubCategoriaDTO.builder()
            .id(rs.getLong("id"))
            .idCategoria(rs.getLong("categoria_id"))
            .nombre(rs.getString("nombre"))
            .descripcion(rs.getString("descripcion"))
            .fechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime())
            .fechaActualizacion(rs.getTimestamp("fecha_actualizacion").toLocalDateTime())
            .activa(rs.getBoolean("activa"))
            .build();

    private List<SubCategoriaDTO> ejecutarConsultaListaSubCategorias(String sql, Object... parametros) {
        try {
            log.info("Ejecutando consulta: {}", sql);
            return jdbcTemplate.query(sql, rowMapper, parametros);
        } catch (EmptyResultDataAccessException e) {
            log.error("Error al ejecutar la consulta: {}", sql, e);
            throw new RecursoNoEncontradoException("SubCategorias", "filtro aplicado", null);
        }
    }

    @Override
    public List<SubCategoriaDTO> obtenerSubcategorias(int pagina) {
        int offset = (pagina - 1) * 10; // Asumiendo que cada página tiene 10 subcategorias
        return ejecutarConsultaListaSubCategorias("SELECT * FROM subcategoria LIMIT 10 OFFSET ?", offset);
    }

    @Override
    public List<SubCategoriaDTO> obtenerTodasLasSubcategorias() {
        return ejecutarConsultaListaSubCategorias("SELECT * FROM subcategoria");
    }

    @Override
    public List<SubCategoriaDTO> obtenerSubcategoriasPorCategoria(long idCategoria) {
        return ejecutarConsultaListaSubCategorias("SELECT * FROM subcategoria WHERE categoria_id = ?", idCategoria);
    }

    @Override
    public SubCategoriaDTO obtenerSubcategoriaPorId(long id) {
        try{
            log.info("Obteniendo subcategoria por ID: {}", id);
            return jdbcTemplate.queryForObject("SELECT * FROM subcategoria WHERE id = ?", rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Error al ejecutar la consulta: {}", e.getMessage());
            throw new RecursoNoEncontradoException("SubCategorias", "filtro aplicado", String.valueOf(id));
        }
    }

    @Override
    public SubCategoriaDTO crearSubCategoria (CreateSubCategoriaDTO subcategoriaDTO) {

        Map<String, Object> fields = new LinkedHashMap<>();
        fields.put("categoria_id", subcategoriaDTO.getIdCategoria());
        fields.put("nombre", subcategoriaDTO.getNombre());
        fields.put("descripcion", subcategoriaDTO.getDescripcion());
        fields.put("fecha_registro", Timestamp.valueOf(LocalDateTime.now()));
        fields.put("fecha_actualizacion", Timestamp.valueOf(LocalDateTime.now()));
        fields.put("activa", true);

        String sql = DynamicSqlBuilder.buildInsertSql("subcategoria", fields);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        log.info("Creando subcategoria con datos: {}", fields);

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
            log.error("Error al crear la subcategoria, clave generada es nula");
            throw new RecursoNoEncontradoException("SubCategorias", "filtro aplicado", null);
        }

        return obtenerSubcategoriaPorId(key.longValue());
        
    }

    @Override
    public void actualizarSubcategoria(long id, UpdateSubCategoriaDTO subcategoriaDTO){

        Map<String, Object> fields = new LinkedHashMap<>();
        
        if (subcategoriaDTO.getIdCategoria() != null) {
            fields.put("categoria_id", subcategoriaDTO.getIdCategoria());
        }

        if (subcategoriaDTO.getNombre() != null) {
            fields.put("nombre", subcategoriaDTO.getNombre());
        }
        if (subcategoriaDTO.getDescripcion() != null) {
            fields.put("descripcion", subcategoriaDTO.getDescripcion());
        }

        if (subcategoriaDTO.getActiva() != null) {
            fields.put("activa", subcategoriaDTO.getActiva());
        }

        if (fields.isEmpty()) {
            log.error("No se proporcionaron campos para actualizar la subcategoria");
            return;
        }

        fields.put("fecha_actualizacion", Timestamp.valueOf(LocalDateTime.now()));

        String sql = DynamicSqlBuilder.buildUpdateSql("subcategoria", fields, "id = ?");

        jdbcTemplate.update(sql, id);
    }

    @Override
    public void borrarSubcategoria(long id){

        if(!existeSubcategoria(id)){
            log.error("Subcategoria con ID {} no encontrado", id);
            throw new RecursoNoEncontradoException("SubCategorias", "filtro aplicado", String.valueOf(id));
        }

        log.info("Borrando subcategoria con ID: {}", id);

        String sql = DynamicSqlBuilder.buildDeleteSql("subcategoria", "id = ?");
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existeSubcategoria(long id){
        String sql = DynamicSqlBuilder.buildCountSql("subcategoria", "id = ?");
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
    
}
