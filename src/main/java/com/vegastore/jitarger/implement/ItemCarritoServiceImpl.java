package com.vegastore.jitarger.implement;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

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

@Service
public class ItemCarritoServiceImpl implements ItemCarritoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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

    @Override
    public List<ItemCarritoDTO> obtenerTodosLosItemsCarrito() {
        String sql = "SELECT * FROM item_carrito";
        return jdbcTemplate.query(sql, itemCarritoRowMapper);
    }

    private static final int PAGE_SIZE = 10;

    @Override
    public List<ItemCarritoDTO> obtenerItemsCarrito(int pagina) {
        int offset = (pagina - 1) * PAGE_SIZE;
        String sql = "SELECT * FROM item_carrito ORDER BY id LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, itemCarritoRowMapper, PAGE_SIZE, offset);
    }

    @Override
    public List<ItemCarritoDTO> obtenerItemsCarritoPorCarrito(int pag, long idCarrito) {
        int offset = (pag - 1) * PAGE_SIZE;
        String sql = "SELECT * FROM item_carrito WHERE id_carrito = ? ORDER BY id LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, itemCarritoRowMapper, idCarrito, PAGE_SIZE, offset);
    }

    @Override
    public List<ItemCarritoDTO> obtenerItemsCarritoPorCarritoSiEsActivo(int pagina, long idCarrito) {
        int offset = (pagina - 1) * PAGE_SIZE;
        String sql = "SELECT * FROM item_carrito WHERE id_carrito = ? AND activo = 1 ORDER BY id LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, itemCarritoRowMapper, idCarrito, PAGE_SIZE, offset);
    }

    @Override
    public List<ItemCarritoDTO> obtenerItemsCarritoPorCarritoSiNoEsActivo(int pagina, long idCarrito) {
        int offset = (pagina - 1) * PAGE_SIZE;
        String sql = "SELECT * FROM item_carrito WHERE id_carrito = ? AND activo = 0 ORDER BY id LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, itemCarritoRowMapper, idCarrito, PAGE_SIZE, offset);
    }

    @Override
    public ItemCarritoDTO obtenerItemCarritoPorId(long id) {
        String sql = "SELECT * FROM item_carrito WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, itemCarritoRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecursoNoEncontradoException("Item carrito", "id", id);
        }
    }

    @Override
    public ItemCarritoDTO crearItemCarrito(CreateItemCarritoDTO carritoDTO) {
        String sql = """
                INSERT INTO item_carrito (
                id_carrito,
                id_producto_presentacion,
                cantidad,
                fecha_agregado,
                precio_actual,
                nombre_producto,
                unidadmedida_presentacion,
                activo
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, carritoDTO.getIdCarrito());
            ps.setLong(2, carritoDTO.getIdProductoPresentacion());
            ps.setBigDecimal(3, carritoDTO.getCantidad());
            ps.setTimestamp(4, Timestamp.valueOf(carritoDTO.getFechaAgregado()));
            ps.setBigDecimal(5, carritoDTO.getPrecioActual());
            ps.setString(6, carritoDTO.getNombreProducto());
            ps.setString(7, carritoDTO.getUnidadmedidaPresentacion());
            ps.setBoolean(8, carritoDTO.isActivo());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new RuntimeException("Error al crear el item del carrito, no se generó la clave.");
        }

        return obtenerItemCarritoPorId(key.longValue());
    }

    @Override
    public void actualizarItemCarrito(long id, UpdateItemCarritoDTO carritoDTO) {

        if (!existeItemCarrito(id)) {
            throw new RecursoNoEncontradoException("Item carrito", "id", id);
        }

        String sql = """
                UPDATE item_carrito
                SET
                    id_producto_presentacion = ?,
                    cantidad = ?,
                    precio_actual = ?,
                    nombre_producto = ?,
                    unidadmedida_presentacion = ?,
                    activo = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(sql,
                carritoDTO.getIdProductoPresentacion(),
                carritoDTO.getCantidad(),
                carritoDTO.getPrecioActual(),
                carritoDTO.getNombreProducto(),
                carritoDTO.getUnidadmedidaPresentacion(),
                carritoDTO.isActivo(),
                id);

    }

    @Override
    public void borrarItemCarrito(long id) {
        if (!existeItemCarrito(id)) {
            throw new RecursoNoEncontradoException("Item carrito", "id", id);
        }

        String sql = "DELETE FROM item_carrito WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existeItemCarrito(long id) {
        String sql = "SELECT COUNT(id) FROM item_carrito WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

}
