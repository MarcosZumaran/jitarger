package com.vegastore.jitarger.service;

import com.vegastore.jitarger.dto.base.CarritoDTO;
import com.vegastore.jitarger.dto.create.CreateCarritoDTO;
import com.vegastore.jitarger.dto.update.UpdateCarritoDTO;

public interface CarritoService {
    
    CarritoDTO obtenerCarritoPorId(long id);
    CarritoDTO obtenerCarritoPorUsuarioId(long id);
    CarritoDTO crearCarrito(CreateCarritoDTO carritoDTO);
    void actualizarCarrito(long id, UpdateCarritoDTO carritoDTO);
    void borrarCarrito(long id);

}
