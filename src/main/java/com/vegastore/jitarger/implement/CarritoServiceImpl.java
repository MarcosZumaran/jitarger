package com.vegastore.jitarger.implement;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.vegastore.jitarger.util.DynamicSqlBuilder;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger log = LoggerFactory.getLogger(CarritoServiceImpl.class);

    // Row mapper para mapear los resultados de la consulta a un objeto CarritoDTO
    private final RowMapper<CarritoDTO> carritoRowMapper = (rs, rowNum) -> CarritoDTO.builder()
            .id(rs.getLong("id"))
            .idUsuario(rs.getLong("id_usuario"))
            .estado(rs.getString("estado"))
            .fechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime())
            .fechaCambioEstado(rs.getTimestamp("fecha_cambio_estado").toLocalDateTime())
            .build();


    private List<CarritoDTO> ejecutarConsultaListaCarritos(String sql, Object... parametros) {
        try {
            log.info("Ejecutando consulta: {}", sql);
            return jdbcTemplate.query(sql, carritoRowMapper, parametros);
        } catch (EmptyResultDataAccessException e) {
            log.error("Error al ejecutar la consulta: {}", e.getMessage());
            throw new RecursoNoEncontradoException("Carritos", "filtro aplicado", null);
        }
    }

    private static final Set<String> ESTADOS_VALIDOS = Set.of("ACTIVO", "PROCESADO", "CANCELADO");

    private void validarEstado(String estado) {
        if (estado == null || !ESTADOS_VALIDOS.contains(estado.toUpperCase())) {
            log.error("Estado inválido: {}", estado);
            throw new IllegalArgumentException("Estado inválido: " + estado);
        }
    }

    @Override
    public List<CarritoDTO> obtenerCarritos(){
        return ejecutarConsultaListaCarritos("SELECT * FROM carrito");
    }

    @Override
    public List<CarritoDTO> obtenerCarritosPorUsuarioId(long id){
        return ejecutarConsultaListaCarritos("SELECT * FROM carrito WHERE id_usuario = ?", id);
    }

    @Override
    public CarritoDTO obtenerCarritoPorEstadoPorUsuarioId(long id, String estado){
        try {
            validarEstado(estado);
            log.info("Obteniendo carrito por ID: {}", id);
            return jdbcTemplate.queryForObject("SELECT * FROM carrito WHERE id_usuario = ? AND estado = ?", carritoRowMapper, id, estado);
        } catch (EmptyResultDataAccessException e) {
            log.error("Error al ejecutar la consulta: {}", e.getMessage());
            throw new RecursoNoEncontradoException("Carrito", "id", id);
        }
    }

    @Override
    public CarritoDTO obtenerCarritoPorId(long id){
        try {
            log.info("Obteniendo carrito por ID: {}", id);
            return jdbcTemplate.queryForObject("SELECT * FROM carrito WHERE id = ?", carritoRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Error al ejecutar la consulta: {}", e.getMessage());
            throw new RecursoNoEncontradoException("Carrito", "id", id);
        }
    }

    @Override
    public CarritoDTO crearCarrito(CreateCarritoDTO carritoDTO){

        Map<String, Object> fields = new LinkedHashMap<>();

        fields.put("id_usuario", carritoDTO.getIdUsuario());
        fields.put("estado", carritoDTO.getEstado());
        fields.put("fecha_creacion", Timestamp.valueOf(LocalDateTime.now()));
        fields.put("fecha_cambio_estado", Timestamp.valueOf(LocalDateTime.now()));

        String sql = DynamicSqlBuilder.buildInsertSql("carrito", fields);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        log.info("Creando carrito con datos: {}", fields);

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
            log.error("Error al crear el carrito, clave generada es nula");
            throw new RecursoNoEncontradoException("Carritos", "filtro aplicado", null);
        }

        return obtenerCarritoPorId(key.longValue());

    }

    @Override
    public void actualizarCarrito(long id, UpdateCarritoDTO carritoDTO){

        Map<String, Object> fields = new LinkedHashMap<>();

        fields.put("estado", carritoDTO.getEstado());
        fields.put("fecha_cambio_estado", Timestamp.valueOf(LocalDateTime.now()));

        String sql = DynamicSqlBuilder.buildUpdateSql("carrito", fields, "id = ?");

        log.info("Actualizando carrito con datos: {}", fields);

        jdbcTemplate.update(sql, carritoDTO.getEstado(), Timestamp.valueOf(LocalDateTime.now()), id);
    }

    @Override
    public void borrarCarrito(long id){
        if (!existeCarrito(id)) {
            log.warn("No se encontró el carrito con ID: {}", id);
            throw new RecursoNoEncontradoException("Carrito", "ID", id);
        }
        log.info("Borrando carrito con ID: {}", id);
        String sql = DynamicSqlBuilder.buildDeleteSql("carrito", "id = ?");
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existeCarrito(long id){
        String sql = DynamicSqlBuilder.buildCountSql("carrito", "id = ?");
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

}
