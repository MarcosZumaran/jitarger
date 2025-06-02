package com.vegastore.jitarger.service;

import java.util.List;

import com.vegastore.jitarger.dto.base.ItemCarritoDTO;
import com.vegastore.jitarger.dto.create.CreateCarritoDTO;
import com.vegastore.jitarger.dto.update.UpdateItemCarritoDTO;

public interface ItemCarritoService {
    List<ItemCarritoDTO> obtenerItemsCarritoPorCarrito(long idCarrito);
    List<ItemCarritoDTO> obtenerItemsCarritoPorCarritoSiEsActivo(long idCarrito, boolean activo);
    List<ItemCarritoDTO> obtenerItemsCarritoPorCarritoSiNoEsActivo(long idCarrito, boolean activo);
    ItemCarritoDTO obtenerItemCarritoPorId(long id);
    long crearItemCarrito(CreateCarritoDTO carritoDTO);
    void actualizarItemCarrito(long id, UpdateItemCarritoDTO carritoDTO);
    void borrarItemCarrito(long id);
    boolean existeItemCarrito(long id);
}
