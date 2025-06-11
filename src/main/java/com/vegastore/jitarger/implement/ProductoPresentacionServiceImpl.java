package com.vegastore.jitarger.implement;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

import com.vegastore.jitarger.dto.base.ProductoPresentacionDTO;
import com.vegastore.jitarger.dto.create.CreateProductoPresentacionDTO;
import com.vegastore.jitarger.dto.update.UpdateProductoPresentacionDTO;
import com.vegastore.jitarger.exception.RecursoNoEncontradoException;
import com.vegastore.jitarger.service.ProductoPresentacionService;
import com.vegastore.jitarger.util.DynamicSqlBuilder;

@Service
public class ProductoPresentacionServiceImpl implements ProductoPresentacionService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger log = LoggerFactory.getLogger(ProductoPresentacionServiceImpl.class);

    private final RowMapper<ProductoPresentacionDTO> rowMapper = (rs, rowNum) -> ProductoPresentacionDTO.builder()
            .id(rs.getLong("id"))
            .idProducto(rs.getLong("id_producto"))
            .unidadMedida(rs.getString("unidad_medida"))
            .unidadAbreviatura(rs.getString("unidad_abreviatura"))
            .equivalencia(rs.getBigDecimal("equivalencia"))
            .precio(rs.getBigDecimal("precio"))
            .disponible(rs.getBoolean("disponible"))
            .fechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime())
            .fechaActualizacion(rs.getTimestamp("fecha_actualizacion").toLocalDateTime())
            .build();
    
    private List<ProductoPresentacionDTO> ejecutarConsultaListaPresentaciones(String sql, Object... parametros) {
        try{
            log.info("Ejecutando consulta: {}", sql);
            return jdbcTemplate.query(sql, rowMapper, parametros);
        } catch (EmptyResultDataAccessException e) {
            log.error("Error al ejecutar la consulta: {}", e.getMessage());
            throw new RecursoNoEncontradoException("Presentaciones de Productos", "filtro aplicado", null);
        }
    }

    @Override
    public List<ProductoPresentacionDTO> obtenerPresentacionesPorProducto(long idProducto) {;
        return ejecutarConsultaListaPresentaciones("SELECT * FROM producto_presentacion WHERE id_producto = ?", idProducto);
    }

    @Override
    public ProductoPresentacionDTO obtenerPresentacionPorId(long id) {
        try{
            log.info("Obteniendo presentacion por ID: {}", id);
            return jdbcTemplate.queryForObject("SELECT * FROM producto_presentacion WHERE id = ?", rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Error al ejecutar la consulta: {}", e.getMessage());
            throw new RecursoNoEncontradoException("Presentaciones de Productos", "filtro aplicado", String.valueOf(id));
        }
    }

    @Override
    public ProductoPresentacionDTO crearPresentacion(CreateProductoPresentacionDTO presentacionDTO) {

        Map<String, Object> fields = new LinkedHashMap<>();

        fields.put("id_producto", presentacionDTO.getIdProducto());
        fields.put("unidad_medida", presentacionDTO.getUnidadMedida());
        fields.put("unidad_abreviatura", presentacionDTO.getUnidadAbreviatura());
        fields.put("equivalencia", presentacionDTO.getEquivalencia());
        fields.put("precio", presentacionDTO.getPrecio());
        fields.put("disponible", presentacionDTO.isDisponible());
        fields.put("fecha_creacion", Timestamp.valueOf(LocalDateTime.now()));
        fields.put("fecha_actualizacion", Timestamp.valueOf(LocalDateTime.now()));

        String sql = DynamicSqlBuilder.buildInsertSql("producto_presentacion", fields);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        log.info("Creando presentacion con datos: {}", fields);

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
            log.error("Error al crear la presentacion, clave generada es nula");
            throw new RuntimeException("Error al crear la presentacion, clave generada es nula");
        }

        return obtenerPresentacionPorId(key.longValue());

    }

    @Override
    public void actualizarPresentacion(long id, UpdateProductoPresentacionDTO presentacionDTO) {
        
        if (!existePresentacion(id)) {
            log.error("Presentacion con ID {} no encontrada", id);
            throw new RecursoNoEncontradoException("Presentaciones de Productos", "id", String.valueOf(id));
        }
        
        Map<String, Object> fields = new LinkedHashMap<>();

        if (presentacionDTO.getUnidadMedida() != null)
            fields.put("unidad_medida", presentacionDTO.getUnidadMedida());

        if (presentacionDTO.getUnidadAbreviatura() != null)
            fields.put("unidad_abreviatura", presentacionDTO.getUnidadAbreviatura());

        if (presentacionDTO.getEquivalencia() != null)
            fields.put("equivalencia", presentacionDTO.getEquivalencia());

        if (presentacionDTO.getPrecio() != null)
            fields.put("precio", presentacionDTO.getPrecio());

        if (presentacionDTO.getDisponible() != null)
            fields.put("disponible", presentacionDTO.getDisponible());

        if(fields.isEmpty()){
            log.warn("No se proporcionaron campos para actualizar la presentacion con ID: {}", id);
            return;
        }

        fields.put("fecha_actualizacion", Timestamp.valueOf(LocalDateTime.now()));

        String sql = DynamicSqlBuilder.buildUpdateSql("producto_presentacion", fields, "id = ?");

        List<Object> parametros = new ArrayList<>(fields.values());
        parametros.add(id);

        log.info("Actualizando presentacion con ID: {} con datos: {}", id, fields);

        jdbcTemplate.update(sql, parametros.toArray());

    }

    @Override
    public void borrarPresentacion(long id) {
        String sql = DynamicSqlBuilder.buildDeleteSql("producto_presentacion", "id = ?");
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existePresentacion(long id) {
        String sql = DynamicSqlBuilder.buildCountSql("producto_presentacion", "id = ?");
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }


}
