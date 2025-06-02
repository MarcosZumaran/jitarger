package com.vegastore.jitarger.service;

import java.util.List;

import com.vegastore.jitarger.dto.base.ProductoPresentacionDTO;
import com.vegastore.jitarger.dto.create.CreateProductoPresentacionDTO;
import com.vegastore.jitarger.dto.update.UpdateProductoPresentacionDTO;

public interface ProductoPresentacionService {

    List<ProductoPresentacionDTO> obtenerPresentacionesPorProducto(long idProducto);
    ProductoPresentacionDTO obtenerPresentacionPorId(long id);
    ProductoPresentacionDTO crearPresentacion(CreateProductoPresentacionDTO presentacionDTO);
    void actualizarPresentacion(long id, UpdateProductoPresentacionDTO presentacionDTO);
    void borrarPresentacion(long id);
    boolean existePresentacion(long id);
    
}