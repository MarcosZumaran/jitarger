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

import com.vegastore.jitarger.dto.base.ItemCarritoDTO;
import com.vegastore.jitarger.dto.create.CreateItemCarritoDTO;
import com.vegastore.jitarger.dto.update.UpdateItemCarritoDTO;
import com.vegastore.jitarger.exception.RecursoNoEncontradoException;
import com.vegastore.jitarger.service.ItemCarritoService;
import com.vegastore.jitarger.util.DynamicSqlBuilder;

@Service
public class ItemCarritoServiceImpl implements ItemCarritoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger log = LoggerFactory.getLogger(ItemCarritoServiceImpl.class);

    private final RowMapper<ItemCarritoDTO> itemCarritoRowMapper = (rs, rowNum) -> ItemCarritoDTO.builder()
            .id(rs.getLong("id"))
            .idCarrito(rs.getLong("id_carrito"))
            .idProductoPresentacion(rs.getLong("id_producto_presentacion"))
            .cantidad(rs.getBigDecimal("cantidad"))
            .fechaAgregado(rs.getTimestamp("fecha_agregado").toLocalDateTime())
            .precioActual(rs.getBigDecimal("precio_actual"))
            .nombreProducto(rs.getString("nombre_producto"))
            .unidadmedidaPresentacion(rs.getString("unidadmedida_presentacion"))
            .activo(rs.getBoolean("activo"))
            .build();

    List<ItemCarritoDTO> ejecutarConsultaListaItemsCarrito(String sql, Object... parametros) {
        try{
            log.info("Ejecutando consulta: {}", sql);
            return jdbcTemplate.query(sql, itemCarritoRowMapper, parametros);
        } catch (EmptyResultDataAccessException e) {
            log.error("Error al ejecutar la consulta: {}", e.getMessage());
            throw new RecursoNoEncontradoException("ItemsCarrito", "filtro aplicado", null);
        }
    }

    @Override
    public List<ItemCarritoDTO> obtenerTodosLosItemsCarrito(){
        return ejecutarConsultaListaItemsCarrito("SELECT * FROM item_carrito");
    }

    @Override
    public List<ItemCarritoDTO> obtenerItemsCarrito(int pagina){
        int offset = (pagina - 1) * 10;
        return ejecutarConsultaListaItemsCarrito("SELECT * FROM item_carrito LIMIT 10 OFFSET ?", offset);
    }

    @Override
    public List<ItemCarritoDTO> obtenerItemsCarritoPorCarrito(int pag, long idCarrito){
        int offset = (pag - 1) * 10;
        return ejecutarConsultaListaItemsCarrito("SELECT * FROM item_carrito WHERE id_carrito = ? ORDER BY id LIMIT 10 OFFSET ?", idCarrito, offset);
    }

    @Override
    public List<ItemCarritoDTO> obtenerItemsCarritoPorCarritoSiEsActivo(int pagina, long idCarrito){
        int offset = (pagina - 1) * 10;
        return ejecutarConsultaListaItemsCarrito("SELECT * FROM item_carrito WHERE id_carrito = ? AND activo = 1 ORDER BY id LIMIT 10 OFFSET ?", idCarrito, offset);
    }

    @Override
    public List<ItemCarritoDTO> obtenerItemsCarritoPorCarritoSiNoEsActivo(int pagina, long idCarrito){
        int offset = (pagina - 1) * 10;
        return ejecutarConsultaListaItemsCarrito("SELECT * FROM item_carrito WHERE id_carrito = ? AND activo = 0 ORDER BY id LIMIT 10 OFFSET ?", idCarrito, offset);
    }

    @Override
    public ItemCarritoDTO obtenerItemCarritoPorId(long id){
        try{
            log.info("Obteniendo item carrito por ID: {}", id);
            return jdbcTemplate.queryForObject("SELECT * FROM item_carrito WHERE id = ?", itemCarritoRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Error al ejecutar la consulta: {}", e.getMessage());
            throw new RecursoNoEncontradoException("ItemCarrito", "id", id);
        }
    }

    @Override
    public ItemCarritoDTO crearItemCarrito(CreateItemCarritoDTO carritoDTO){

        Map <String, Object> fields = new LinkedHashMap<>();

        fields.put("id_carrito", carritoDTO.getIdCarrito());
        fields.put("id_producto_presentacion", carritoDTO.getIdProductoPresentacion());
        fields.put("cantidad", carritoDTO.getCantidad());
        fields.put("fecha_agregado", Timestamp.valueOf(LocalDateTime.now()));
        fields.put("precio_actual", carritoDTO.getPrecioActual());
        fields.put("activo", true);

        String sql = DynamicSqlBuilder.buildInsertSql("item_carrito", fields);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        log.info("Creando item carrito con datos: {}", fields);

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
            log.error("Error al crear el item carrito, clave generada es nula");
            throw new RecursoNoEncontradoException("ItemCarrito", "filtro aplicado", null);
        }

        return obtenerItemCarritoPorId(key.longValue());

    }

    @Override
    public void actualizarItemCarrito(long id, UpdateItemCarritoDTO carritoDTO){

        Map<String, Object> fields = new LinkedHashMap<>();

        if (carritoDTO.getCantidad() != null) fields.put("cantidad", carritoDTO.getCantidad());
        if (carritoDTO.getPrecioActual() != null) fields.put("precio_actual", carritoDTO.getPrecioActual());
        if (carritoDTO.getActivo() != null) fields.put("activo", carritoDTO.getActivo());

        if (fields.isEmpty()) {
            log.error("No se proporcionaron campos para actualizar el item carrito");
            return;
        }

        String sql = DynamicSqlBuilder.buildUpdateSql("item_carrito", fields, "id = ?");

        Object[] params = Stream.concat(fields.values().stream(), Stream.of(id)).toArray();

        log.info("Actualizando item carrito con datos: {}", fields);

        jdbcTemplate.update(sql, params);

    }

    @Override
    public void borrarItemCarrito(long id){
        if (!existeItemCarrito(id)){
            throw new RecursoNoEncontradoException("ItemCarrito", "filtro aplicado", String.valueOf(id));
        }
        log.info("Borrando item carrito con ID: {}", id);
        String sql = DynamicSqlBuilder.buildDeleteSql("item_carrito", "id = ?");
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existeItemCarrito(long id){
        String sql = DynamicSqlBuilder.buildCountSql("item_carrito", "id = ?");
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

}
