package com.vegastore.jitarger.implement;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

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
    public LoteDTO crearLote(CreateLoteDTO loteDTO) {

        final LocalDateTime fechaAcual = LocalDateTime.now();

        String sql = """
                INSERT INTO lote (
                id_producto,
                id_proveedor,
                unidad_medida_base,
                unidad_medida_abreviatura,
                costo,
                cantidad_inicial,
                stock,
                fecha_registro,
                fecha_actualizacion
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        ;

        log.info("Creando lote con datos: {}", loteDTO);

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, loteDTO.getIdProducto());
                ps.setLong(2, loteDTO.getIdProveedor());
                ps.setString(3, loteDTO.getUnidadMedidaBase());
                ps.setString(4, loteDTO.getUnidadMedidaAbreviatura());
                ps.setBigDecimal(5, loteDTO.getCosto());
                ps.setBigDecimal(7, loteDTO.getCantidadInicial());
                ps.setBigDecimal(8, loteDTO.getStock());
                ps.setTimestamp(9, Timestamp.valueOf(fechaAcual));
                ps.setTimestamp(10, Timestamp.valueOf(fechaAcual));
                return ps;
            }, keyHolder);

            Number generatedId = keyHolder.getKey();
            if (generatedId == null) {
                throw new RuntimeException("Error al crear el lote");
            }

            log.info("Lote creado con ID: {}", generatedId.longValue());

            return obtenerLotePorId(generatedId.longValue());
        } catch (Exception e) {
            log.error("Error al crear el lote: {}", e.getMessage());
            throw new RuntimeException("Error al crear el lote: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizarLote(long id, UpdateLoteDTO loteDTO) {

        if (!existeLote(id)) {
            log.info("Lote con ID {} no encontrado para actualización", id);
            throw new RecursoNoEncontradoException("Lote", "id", id);
        }

        final LocalDateTime fechaAcual = LocalDateTime.now();

        try {
            String sql = """
                    UPDATE lote
                    SET
                        unidad_medida_base = ?,
                        unidad_medida_abreviatura = ?,
                        costo = ?,
                        cantidad_inicial = ?,
                        stock = ?,
                        fecha_actualizacion = ?
                    WHERE id = ?
                    """;

            jdbcTemplate.update(sql,
                    loteDTO.getUnidadMedidaBase(),
                    loteDTO.getUnidadMedidaAbreviatura(),
                    loteDTO.getCosto(),
                    loteDTO.getCantidadInicial(),
                    loteDTO.getStock(),
                    Timestamp.valueOf(fechaAcual),
                    id);

            log.info("Lote con ID {} actualizado correctamente", id);

        } catch (Exception e) {
            log.error("Error al actualizar el lote: {}", e.getMessage());
            throw new RuntimeException("Error al actualizar el lote: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarLote(long id) {
        if (!existeLote(id)) {
            throw new RecursoNoEncontradoException("Lote", "id", id);
        }
        try {
            String sql = "DELETE FROM lote WHERE id = ?";
            jdbcTemplate.update(sql, id);
            log.info("Lote con ID {} eliminado correctamente", id);
        } catch (Exception e) {
            log.error("Error al eliminar el lote: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar el lote: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existeLote(long id) {
        String sql = "SELECT COUNT(id) FROM lote WHERE id = ?";
        try {
            log.info("Verificando existencia del lote con ID {}", id);
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);

            if (count == null || count == 0) {
                log.warn("El lote con ID {} no existe", id);
                return false;
            }

            log.info("El lote con ID {} existe", id);
            return true;
        } catch (Exception e) {
            log.error("Error al verificar la existencia del lote con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al verificar la existencia del lote: " + e.getMessage(), e);
        }
    }
}
