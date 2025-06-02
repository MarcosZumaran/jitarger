package com.vegastore.jitarger.service;

import com.vegastore.jitarger.dto.base.CarritoDTO;
import com.vegastore.jitarger.dto.create.CreateCarritoDTO;

public interface CarritoService {
    CarritoDTO obtenerCarritoPorId(long id);
    CarritoDTO obtenerCarritoPorUsuarioId(long id);
    CarritoDTO crearCarrito(CreateCarritoDTO carritoDTO);
    void borrarCarrito(long id);
    boolean existeCarritoParaUsuario(long idUsuario);
}
