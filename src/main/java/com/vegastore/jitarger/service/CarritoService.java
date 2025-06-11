package com.vegastore.jitarger.service;

import java.util.List;

import com.vegastore.jitarger.dto.base.CarritoDTO;
import com.vegastore.jitarger.dto.create.CreateCarritoDTO;
import com.vegastore.jitarger.dto.update.UpdateCarritoDTO;

public interface CarritoService {
    
    List<CarritoDTO> obtenerCarritos();
    List<CarritoDTO> obtenerCarritosPorUsuarioId(long id);
    CarritoDTO obtenerCarritoPorEstadoPorUsuarioId(long id, String estado);
    CarritoDTO obtenerCarritoPorId(long id);
    CarritoDTO crearCarrito(CreateCarritoDTO carritoDTO);
    void actualizarCarrito(long id, UpdateCarritoDTO carritoDTO);
    void borrarCarrito(long id);
    boolean existeCarrito(long id);

}
