package com.vegastore.jitarger.service;

import com.vegastore.jitarger.dto.base.CarritoDTO;
import com.vegastore.jitarger.dto.create.CreateCarritoDTO;
import com.vegastore.jitarger.dto.update.UpdateCategoriaDTO;

public interface CarritoService {
    CarritoDTO obtenerCarritoPorId(long id);
    CarritoDTO obtenerCarritoPorUsuarioId(long id);
    long crearCarrito(CreateCarritoDTO carritoDTO);
    void actualizarCarrito(long id, UpdateCategoriaDTO carritoDTO);
    void borrarCarrito(long id);
    boolean existeCarrito(long id);
}
