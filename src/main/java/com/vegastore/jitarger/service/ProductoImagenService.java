package com.vegastore.jitarger.service;

import java.util.List;

import com.vegastore.jitarger.dto.base.ProductoImagenDTO;
import com.vegastore.jitarger.dto.create.CreateProductoImagenDTO;
import com.vegastore.jitarger.dto.update.UpdateProductoImagenDTO;

public interface ProductoImagenService {

    List<ProductoImagenDTO> obtenerImagenesPorProducto(long idProducto);
    List<ProductoImagenDTO> obtenerImagenesPorTipo(String tipo);
    ProductoImagenDTO obtenerImagenPorId(long id);
    long crearImagen(CreateProductoImagenDTO imagenDTO);
    void actualizarImagen(long id, UpdateProductoImagenDTO imagenDTO);
    void borrarImagen(long id);
    boolean existeImagen(long id);
    
}