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

import com.vegastore.jitarger.dto.base.UsuarioDTO;
import com.vegastore.jitarger.dto.create.CreateUsuarioDTO;
import com.vegastore.jitarger.dto.update.UpdateUsuarioDTO;
import com.vegastore.jitarger.exception.RecursoNoEncontradoException;
import com.vegastore.jitarger.service.UsuarioService;
import com.vegastore.jitarger.util.DynamicSqlBuilder;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    private final RowMapper<UsuarioDTO> rowMapper = (rs, rowNum) -> UsuarioDTO.builder()
            .id(rs.getLong("id"))
            .nombre(rs.getString("nombre"))
            .apellido(rs.getString("apellido"))
            .correo(rs.getString("correo"))
            .telefono(rs.getString("telefono"))
            .clave(rs.getString("clave"))
            .rol(rs.getString("rol"))
            .activo(rs.getBoolean("activo"))
            .fechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime())
            .fechaActualizacion(rs.getTimestamp("fecha_actualizacion").toLocalDateTime())
            .build();
    
    private List<UsuarioDTO> ejecutarConsultaListaUsuarios(String sql, Object... parametros) {
        try{
            log.info("Ejecutando consulta: {}", sql);            
            return jdbcTemplate.query(sql, rowMapper, parametros);
        } catch (EmptyResultDataAccessException e) {
            log.error("Error al ejecutar la consulta: {}", e.getMessage());
            throw new RecursoNoEncontradoException("Usuarios", "filtro aplicado", null);
        }
    }

    @Override
    public List<UsuarioDTO> obtenerUsuarios(int pagina){
        return ejecutarConsultaListaUsuarios("SELECT * FROM usuario LIMIT 10 OFFSET ?", pagina);
    }

    @Override
    public List<UsuarioDTO> obtenerUsuariosPorNombre(int pagina, String nombre){
        return ejecutarConsultaListaUsuarios("SELECT * FROM usuario WHERE nombre = ? LIMIT 10 OFFSET ?", nombre, pagina);
    }
    
    @Override
    public List<UsuarioDTO> obtenerUsuariosPorRol(int pagina, String rol){
        return ejecutarConsultaListaUsuarios("SELECT * FROM usuario WHERE rol = ? LIMIT 10 OFFSET ?", rol, pagina);
    }

    @Override
    public List<UsuarioDTO> obtenerTodosLosUsuarios(){
        return ejecutarConsultaListaUsuarios("SELECT * FROM usuario");
    }

    @Override
    public List<UsuarioDTO> obtenerTodosLosUsuarioPorNombre(String nombre){
        return ejecutarConsultaListaUsuarios("SELECT * FROM usuario WHERE nombre = ?", nombre);
    }

    @Override
    public List<UsuarioDTO> obtenerTodosLosUsuariosPorRol(String rol){
        return ejecutarConsultaListaUsuarios("SELECT * FROM usuario WHERE rol = ?", rol);
    }

    @Override
    public UsuarioDTO obtenerUsuarioPorId(long id){
        try{
            log.info("Ejecutando consulta: SELECT * FROM usuario WHERE id = {}", id);            
            return jdbcTemplate.queryForObject("SELECT * FROM usuario WHERE id = ?", rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Error al ejecutar la consulta: {}", e.getMessage());
            throw new RecursoNoEncontradoException("Usuarios", "filtro aplicado", null);
        }
    }

    @Override
    public UsuarioDTO obtenerusuarioPorCredenciales(String correo, String clave){
        try{
            log.info("Ejecutando consulta: SELECT * FROM usuario WHERE correo = ? AND clave = ?", correo, clave);            
            return jdbcTemplate.queryForObject("SELECT * FROM usuario WHERE correo = ? AND clave = ?", rowMapper, correo, clave);
        } catch (EmptyResultDataAccessException e) {
            log.error("Error al ejecutar la consulta: {}", e.getMessage());
            throw new RecursoNoEncontradoException("Usuarios", "filtro aplicado", null);
        }
    }

    @Override
    public UsuarioDTO crearUsuario(CreateUsuarioDTO usuarioDTO){

        Map<String, Object> fields = new LinkedHashMap<>();

        fields.put("nombre", usuarioDTO.getNombre());
        fields.put("apellido", usuarioDTO.getApellido());
        fields.put("correo", usuarioDTO.getCorreo());
        fields.put("telefono", usuarioDTO.getTelefono());
        fields.put("clave", usuarioDTO.getClave());
        fields.put("rol", usuarioDTO.getRol());
        fields.put("activo", true);
        fields.put("fecha_registro", Timestamp.valueOf(LocalDateTime.now()));
        fields.put("fecha_actualizacion", Timestamp.valueOf(LocalDateTime.now()));

        String sql = DynamicSqlBuilder.buildInsertSql("usuario", fields);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        log.info("Creando usuario con datos: {}", fields);

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
            log.error("Error al crear el usuario, clave generada es nula");
            throw new RecursoNoEncontradoException("Usuarios", "filtro aplicado", null);
        }
        return obtenerUsuarioPorId(key.longValue());

    }

    @Override
    public void actualizarUsuario(long id, UpdateUsuarioDTO usuarioDTO){

        Map<String, Object> fields = new LinkedHashMap<>();

        if (usuarioDTO.getNombre() != null) {
            fields.put("nombre", usuarioDTO.getNombre());
        }
        if (usuarioDTO.getApellido() != null) {
            fields.put("apellido", usuarioDTO.getApellido());
        }
        if (usuarioDTO.getCorreo() != null) {
            fields.put("correo", usuarioDTO.getCorreo());
        }
        if (usuarioDTO.getTelefono() != null) {
            fields.put("telefono", usuarioDTO.getTelefono());
        }
        if (usuarioDTO.getRol() != null) {
            fields.put("rol", usuarioDTO.getRol());
        }
        if (usuarioDTO.getActivo() != null) {
            fields.put("activo", usuarioDTO.getActivo());
        }
        if (fields.isEmpty()) {
            log.error("No se proporcionaron campos para actualizar el usuario");
            return;
        }

        fields.put("fecha_actualizacion", Timestamp.valueOf(LocalDateTime.now()));

        String sql = DynamicSqlBuilder.buildUpdateSql("usuario", fields, "id = ?");

        jdbcTemplate.update(sql, id);
    }

    @Override
    public void cambiarClave(long id, String nuevaClave){
        String sql = "UPDATE usuario SET clave = ? WHERE id = ?";
        jdbcTemplate.update(sql, nuevaClave, id);
    }

    @Override
    public void borrarUsuario(long id){

        if(!existeUsuario(id)){
            log.error("Usuario con ID {} no encontrado", id);
            throw new RecursoNoEncontradoException("Usuarios", "filtro aplicado", String.valueOf(id));
        }

        log.info("Borrando usuario con ID: {}", id);

        String sql = DynamicSqlBuilder.buildDeleteSql("usuario", "id = ?");
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existeUsuario(long id){
        String sql = DynamicSqlBuilder.buildCountSql("usuario", "id = ?");
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
    
}
