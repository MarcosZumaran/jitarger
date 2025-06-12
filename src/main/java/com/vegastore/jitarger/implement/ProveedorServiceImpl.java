package com.vegastore.jitarger.implement;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

import com.vegastore.jitarger.dto.base.ProveedorDTO;
import com.vegastore.jitarger.dto.create.CreateProveedorDTO;
import com.vegastore.jitarger.dto.update.UpdateProveedorDTO;
import com.vegastore.jitarger.exception.RecursoNoEncontradoException;
import com.vegastore.jitarger.service.ProveedorService;
import com.vegastore.jitarger.util.DynamicSqlBuilder;

@Service
public class ProveedorServiceImpl implements ProveedorService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger log = LoggerFactory.getLogger(ProveedorServiceImpl.class);

    private final RowMapper<ProveedorDTO> rowMapper = (rs, rowNum) -> ProveedorDTO.builder()
            .id(rs.getLong("id"))
            .nombreEmpresa(rs.getString("nombre_empresa"))
            .ruc(rs.getString("ruc"))
            .direccion(rs.getString("direccion"))
            .telefono(rs.getString("telefono"))
            .correo(rs.getString("correo"))
            .fechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime())
            .fechaActualizacion(rs.getTimestamp("fecha_actualizacion").toLocalDateTime())
            .activo(rs.getBoolean("activo"))
            .build();

    private List<ProveedorDTO> ejecutarConsultaListaProveedores(String sql, Object... parametros) {
        try{
            log.info("Ejecutando consulta: {}", sql);
            return jdbcTemplate.query(sql, rowMapper, parametros);
        } catch (EmptyResultDataAccessException e) {
            log.error("Error al ejecutar la consulta: {}", e.getMessage());
            throw new RecursoNoEncontradoException("Proveedores", "filtro aplicado", null);
        }
    }

    @Override
    public List<ProveedorDTO> obtenerTodosLosProveedores() {
        return ejecutarConsultaListaProveedores("SELECT * FROM proveedor");
    }

    @Override
    public List<ProveedorDTO> obtenerProveedores(int pagina) {
        int offset = (pagina - 1) * 10; // Asumiendo que cada página tiene 10 proveedores
        return ejecutarConsultaListaProveedores("SELECT * FROM proveedor LIMIT 10 OFFSET ?", offset);
    } 

    @Override
    public List<ProveedorDTO> obtenerProveedoresPorNombre(String nombre){
        return ejecutarConsultaListaProveedores("SELECT * FROM proveedor WHERE nombre_empresa = ?", nombre);
    }

    @Override
    public List<ProveedorDTO> obtenerProveedoresPorActivo(boolean activo){
        return ejecutarConsultaListaProveedores("SELECT * FROM proveedor WHERE activo = ?", activo);
    }

    @Override
    public ProveedorDTO obtenerProveedorPorId(long id){
        try{
            log.info("Obteniendo proveedor por ID: {}", id);
            return jdbcTemplate.queryForObject("SELECT * FROM proveedor WHERE id = ?", rowMapper, id);
        }catch (EmptyResultDataAccessException e) {
            log.error("Error al ejecutar la consulta: {}", e.getMessage());
            throw new RecursoNoEncontradoException("Proveedores", "filtro aplicado", String.valueOf(id));
        }
    }

    @Override
    public ProveedorDTO obtenerProveedorPorRuc(String ruc){
        try{
            log.info("Obteniendo proveedor por RUC: {}", ruc);
            return jdbcTemplate.queryForObject("SELECT * FROM proveedor WHERE ruc = ?", rowMapper, ruc);
        }catch (EmptyResultDataAccessException e) {
            log.error("Error al ejecutar la consulta: {}", e.getMessage());
            throw new RecursoNoEncontradoException("Proveedores", "filtro aplicado", ruc);
        }
    }

    @Override
    public ProveedorDTO crearProveedor(CreateProveedorDTO proveedorDTO){

        Map<String, Object> fields = new LinkedHashMap<>();

        fields.put("nombre_empresa", proveedorDTO.getNombreEmpresa());
        fields.put("ruc", proveedorDTO.getRuc());
        fields.put("direccion", proveedorDTO.getDireccion());
        fields.put("telefono", proveedorDTO.getTelefono());
        fields.put("correo", proveedorDTO.getCorreo());
        fields.put("activo", true);
        fields.put("fecha_creacion", Timestamp.valueOf(LocalDateTime.now()));
        fields.put("fecha_actualizacion", Timestamp.valueOf(LocalDateTime.now()));

        String sql = DynamicSqlBuilder.buildInsertSql("proveedor", fields);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        log.info("Creando proveedor con datos: {}", fields);

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
            log.error("Error al crear el proveedor, clave generada es nula");
            throw new IllegalStateException("No se pudo obtener el ID generado para el proveedor");
        }

        return obtenerProveedorPorId(key.longValue());

    }

    @Override
    public void actualizarProveedor(long id, UpdateProveedorDTO proveedorDTO){

        Map<String, Object> fields = new LinkedHashMap<>();

        if (proveedorDTO.getNombreEmpresa() != null)
            fields.put("nombre_empresa", proveedorDTO.getNombreEmpresa());

        if (proveedorDTO.getRuc() != null)
            fields.put("ruc", proveedorDTO.getRuc());

        if (proveedorDTO.getDireccion() != null)
            fields.put("direccion", proveedorDTO.getDireccion());

        if (proveedorDTO.getTelefono() != null)
            fields.put("telefono", proveedorDTO.getTelefono());

        if (proveedorDTO.getCorreo() != null)
            fields.put("correo", proveedorDTO.getCorreo());

        if (proveedorDTO.getActivo() != null)
            fields.put("activo", proveedorDTO.getActivo());


        if (fields.isEmpty()) {
            log.error("No se proporcionaron campos para actualizar el proveedor");
            return;
        }

        fields.put("fecha_actualizacion", Timestamp.valueOf(LocalDateTime.now()));

        String sql = DynamicSqlBuilder.buildUpdateSql("proveedor", fields, "id = ?");

        Object[] params = Stream.concat(fields.values().stream(), Stream.of(id)).toArray();

        jdbcTemplate.update(sql, params);
    }

    @Override
    public void borrarProveedor(long id){

        if(!existeProveedor(id)){
            log.error("Proveedor con ID {} no encontrado", id);
            throw new RecursoNoEncontradoException("Proveedores", "filtro aplicado", String.valueOf(id));
        }

        log.info("Borrando proveedor con ID: {}", id);

        String sql = DynamicSqlBuilder.buildDeleteSql("proveedor", "id = ?");
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existeProveedor(long id){
        String sql = DynamicSqlBuilder.buildCountSql("proveedor", "id = ?");
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

}
