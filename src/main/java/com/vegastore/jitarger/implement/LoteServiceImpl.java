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

import com.vegastore.jitarger.dto.base.LoteDTO;
import com.vegastore.jitarger.dto.create.CreateLoteDTO;
import com.vegastore.jitarger.dto.update.UpdateLoteDTO;
import com.vegastore.jitarger.exception.RecursoNoEncontradoException;
import com.vegastore.jitarger.service.LoteService;

@Service
public class LoteServiceImpl implements LoteService{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<LoteDTO> loteRowMapper = (rs, rowNum) -> LoteDTO.builder()
            .id(rs.getLong("id"))
            .idProducto(rs.getLong("id_producto"))
            .idProveedor(rs.getLong("id_proveedor"))
            .unidadMedidaBase(rs.getString("unidad_medida_base"))
            .unidadAbreviatura(rs.getString("unidad_abreviatura"))
            .costo(rs.getBigDecimal("costo"))
            .precio(rs.getBigDecimal("precio"))
            .cantidadInicial(rs.getBigDecimal("cantidad_inicial"))
            .cantidadDisponible(rs.getBigDecimal("cantidad_disponible"))
            .build();

    @Override
    @Transactional(readOnly = true)
    public List<LoteDTO> obtenerLotes() {
        String sql = "SELECT * FROM lote ORDER BY id";
        return jdbcTemplate.query(sql, loteRowMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoteDTO> obtenerLotesPorProducto(long idProducto) {
        String sql = "SELECT * FROM lote WHERE id_producto = ? ORDER BY id";
        return jdbcTemplate.query(sql, loteRowMapper, idProducto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoteDTO> obtenerLotesPorProveedor(long idProveedor) {
        String sql = "SELECT * FROM lote WHERE id_proveedor = ? ORDER BY id";
        return jdbcTemplate.query(sql, loteRowMapper, idProveedor);
    }

    @Override
    @Transactional(readOnly = true)
    public LoteDTO obtenerLotePorId(long id) {
        String sql = "SELECT * FROM lote WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, loteRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecursoNoEncontradoException("Lote no encontrado con ID: " + id, sql, e);
        }
    }

    @Override
    @Transactional
    public LoteDTO crearLote(CreateLoteDTO loteDTO) {
        String sql = "INSERT INTO lote (id_producto, id_proveedor, unidad_medida_base, unidad_abreviatura, costo, precio, cantidad_inicial, cantidad_disponible, fecha_creacion) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();



        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, loteDTO.getIdProducto());
            ps.setLong(2, loteDTO.getIdProveedor());
            ps.setString(3, loteDTO.getUnidadMedidaBase());
            ps.setString(4, loteDTO.getUnidadAbreviatura());
            ps.setBigDecimal(5, loteDTO.getCosto());
            ps.setBigDecimal(6, loteDTO.getPrecio());
            ps.setBigDecimal(7, loteDTO.getCantidadInicial());
            ps.setBigDecimal(8, loteDTO.getCantidadDisponible());
            ps.setObject(9, Timestamp.valueOf(LocalDateTime.now()));
            return ps;
        }, keyHolder);
        
        Number key = keyHolder.getKey();

        if(key == null) {
            throw new RuntimeException("Error al crear el lote, ID no generado.");
        }

        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return obtenerLotePorId(id);
    }

    @Override
    @Transactional
    public void actualizarLote(long id, UpdateLoteDTO loteDTO) {
        if (!existeLote(id)) {
            throw new RecursoNoEncontradoException("Lote no encontrado con ID: " + id, null, loteDTO);
        }
        
        String sql = "UPDATE lote SET unidad_medida_base = ?, unidad_abreviatura = ?, costo = ?, precio = ?, cantidad_inicial = ?, cantidad_disponible = ? WHERE id = ?";
        jdbcTemplate.update(sql, 
            loteDTO.getUnidadMedidaBase(), 
            loteDTO.getUnidadAbreviatura(), 
            loteDTO.getCosto(), 
            loteDTO.getPrecio(), 
            loteDTO.getCantidadInicial(), 
            loteDTO.getCantidadDisponible(), 
            id);
    }

    @Override
    @Transactional
    public void borrarLote(long id) {
        if (!existeLote(id)) {
            throw new RecursoNoEncontradoException("Lote no encontrado con ID: " + id, null, null);
        }
        
        String sql = "DELETE FROM lote WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existeLote(long id) {
        String sql = "SELECT COUNT(*) FROM lote WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

}
