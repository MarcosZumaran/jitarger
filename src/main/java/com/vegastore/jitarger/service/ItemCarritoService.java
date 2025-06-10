package com.vegastore.jitarger.service;

import java.util.List;

import com.vegastore.jitarger.dto.base.ItemCarritoDTO;
import com.vegastore.jitarger.dto.create.CreateItemCarritoDTO;
import com.vegastore.jitarger.dto.update.UpdateItemCarritoDTO;

public interface ItemCarritoService {
    List<ItemCarritoDTO> obtenerTodosLosItemsCarrito();
    List<ItemCarritoDTO> obtenerItemsCarrito(int pagina);
    List<ItemCarritoDTO> obtenerItemsCarritoPorCarrito(int pag, long idCarrito);
    List<ItemCarritoDTO> obtenerItemsCarritoPorCarritoSiEsActivo(int pagina, long idCarrito);
    List<ItemCarritoDTO> obtenerItemsCarritoPorCarritoSiNoEsActivo(int pagina, long idCarrito);
    ItemCarritoDTO obtenerItemCarritoPorId(long id);
    ItemCarritoDTO crearItemCarrito(CreateItemCarritoDTO carritoDTO);
    void actualizarItemCarrito(long id, UpdateItemCarritoDTO carritoDTO);
    void borrarItemCarrito(long id);
    boolean existeItemCarrito(long id);
}
