package com.vegastore.jitarger.implement;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
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

import com.vegastore.jitarger.dto.base.LoteDTO;
import com.vegastore.jitarger.dto.create.CreateLoteDTO;
import com.vegastore.jitarger.dto.update.UpdateLoteDTO;
import com.vegastore.jitarger.exception.RecursoNoEncontradoException;
import com.vegastore.jitarger.service.LoteService;
import com.vegastore.jitarger.util.DynamicSqlBuilder;

@Service
public class LoteServiceImpl implements LoteService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger log = LoggerFactory.getLogger(LoteServiceImpl.class);

    private final RowMapper<LoteDTO> loteRowMapper = (rs, rowNum) -> LoteDTO.builder()
            .id(rs.getLong("id"))
            .idProducto(rs.getLong("id_producto"))
            .idProveedor(rs.getLong("id_proveedor"))
            .unidadMedidaBase(rs.getString("unidad_medida_base"))
            .unidadMedidaAbreviatura(rs.getString("unidad_medida_abreviatura"))
            .costo(rs.getBigDecimal("costo"))
            .cantidadInicial(rs.getBigDecimal("cantidad_inicial"))
            .stock(rs.getBigDecimal("stock"))
            .fechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime())
            .fechaActualizacion(rs.getTimestamp("fecha_actualizacion").toLocalDateTime())
            .build();

    private List<LoteDTO> ejecutarConsultaListaLotes(String sql, Object... parametros) {
        try {
            return jdbcTemplate.query(sql, loteRowMapper, parametros);
        } catch (EmptyResultDataAccessException e) {
            log.warn("No se encontraron resultados para la consulta: {}", e.getMessage());
            throw new RecursoNoEncontradoException("Lotes", "filtro aplicado", null);
        }
    }

    @Override
    public List<LoteDTO> obtenerTodosLosLotes() {
        log.info("Obteniendo todos los lotes");
        return ejecutarConsultaListaLotes("SELECT * FROM lote ORDER BY id");
    }

    @Override
    public List<LoteDTO> obtenerLotes(int pagina) {
        int offset = (pagina - 1) * 10;
        log.info("Obteniendo lotes de la página {}", pagina);
        return ejecutarConsultaListaLotes("SELECT * FROM lote ORDER BY id LIMIT ? OFFSET ?", 10, offset);
    }

    @Override
    public List<LoteDTO> obtenerLotesPorProducto(int pag, long idProducto) {
        int offset = (pag - 1) * 10;
        log.info("Obteniendo lotes para producto ID {}", idProducto);
        return ejecutarConsultaListaLotes("SELECT * FROM lote WHERE id_producto = ? ORDER BY id LIMIT ? OFFSET ?",
                idProducto, 10, offset);
    }

    @Override
    public List<LoteDTO> obtenerLotesPorProveedor(int pag, long idProveedor) {
        int offset = (pag - 1) * 10;
        log.info("Obteniendo lotes para proveedor ID {}", idProveedor);
        return ejecutarConsultaListaLotes("SELECT * FROM lote WHERE id_proveedor = ? ORDER BY id LIMIT ? OFFSET ?",
                idProveedor, 10, offset);
    }

    @Override
    public LoteDTO obtenerLotePorId(long id) {
        String sql = "SELECT * FROM lote WHERE id = ?";
        try {
            log.info("Buscando lote con ID {}", id);
            return jdbcTemplate.queryForObject(sql, loteRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("Lote con ID {} no encontrado: {}", id, e.getMessage());
            throw new RecursoNoEncontradoException("Lote", "id", id);
        }
    }

    @Override
    public LoteDTO crearLote(CreateLoteDTO createLoteDTO) {

        LocalDateTime fechaActual = LocalDateTime.now();

        Map<String, Object> fields = new HashMap<>();
        fields.put("id_producto", createLoteDTO.getIdProducto());
        fields.put("id_proveedor", createLoteDTO.getIdProveedor());
        fields.put("unidad_medida_base", createLoteDTO.getUnidadMedidaBase());
        fields.put("unidad_medida_abreviatura", createLoteDTO.getUnidadMedidaAbreviatura());
        fields.put("costo", createLoteDTO.getCosto());
        fields.put("cantidad_inicial", createLoteDTO.getCantidadInicial());
        fields.put("stock", createLoteDTO.getStock());
        fields.put("fecha_registro", Timestamp.valueOf(fechaActual));
        fields.put("fecha_actualizacion", Timestamp.valueOf(fechaActual));

        String sql = DynamicSqlBuilder.buildInsertSql("lote", fields);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        log.info("Creando lote con datos: {}", fields);

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
            log.error("Error al crear el lote, clave generada es nula");
            throw new RecursoNoEncontradoException("Lotes", "filtro aplicado", null);
        }

        return obtenerLotePorId(key.longValue());

    }

    @Override
    public void actualizarLote(long id, UpdateLoteDTO loteDTO) {
        if (!existeLote(id)) {
            throw new RecursoNoEncontradoException("Lote", "id", id);
        }
        
        Map<String, Object> fields = new HashMap<>();
        if (loteDTO.getUnidadMedidaBase() != null) {
            fields.put("unidad_medida_base", loteDTO.getUnidadMedidaBase());
        }
        if (loteDTO.getUnidadMedidaAbreviatura() != null) {
            fields.put("unidad_medida_abreviatura", loteDTO.getUnidadMedidaAbreviatura());
        }
        if (loteDTO.getCosto() != null) {
            fields.put("costo", loteDTO.getCosto());
        }
        if (loteDTO.getCantidadInicial() != null) {
            fields.put("cantidad_inicial", loteDTO.getCantidadInicial());
        }
        if (loteDTO.getStock() != null) {
            fields.put("stock", loteDTO.getStock());
        }
        if (fields.isEmpty()) {
            log.error("No se proporcionaron campos para actualizar el lote");
            return;
        }

        fields.put("fecha_actualizacion", Timestamp.valueOf(LocalDateTime.now()));

        String sql = DynamicSqlBuilder.buildUpdateSql("lote", fields, "id = ?");

        Object[] params = Stream.concat(fields.values().stream(), Stream.of(id)).toArray();

        log.info("Actualizando lote con ID {} con datos: {}", id, fields);

        jdbcTemplate.update(sql, params);

    }

    @Override
    public void eliminarLote(long id) {
        if (!existeLote(id)) {
            throw new RecursoNoEncontradoException("Lote", "id", id);
        }
        String sql = DynamicSqlBuilder.buildDeleteSql("lote", "id = ?");
        log.info("Eliminando lote con ID {}", id);
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existeLote(long id) {
        String sql = DynamicSqlBuilder.buildCountSql("lote", "id = ?");
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
    
}
