package com.vegastore.jitarger.service;

import java.util.List;

import com.vegastore.jitarger.dto.base.ProductoImagenDTO;
import com.vegastore.jitarger.dto.create.CreateProductoImagenDTO;
import com.vegastore.jitarger.dto.update.UpdateProductoImagenDTO;

public interface ProductoImagenService {

    List<ProductoImagenDTO> obtenerImagenesPorProducto(long idProducto);
    List<ProductoImagenDTO> obtenerImagenesPorProductoYTipo(long idProducto, String tipo);
    List<ProductoImagenDTO> obtenerTodasLasImagenes();
    List<ProductoImagenDTO> obtenerImagenes(int pagina);
    List<ProductoImagenDTO> obtenerTodasLasImagenesPorTipo(String tipo);
    List<ProductoImagenDTO> obtenerImagenesPorTipo(int pagina, String tipo);
    ProductoImagenDTO obtenerImagenPorId(long id);
    ProductoImagenDTO crearImagen(CreateProductoImagenDTO imagenDTO);
    void actualizarImagen(long id, UpdateProductoImagenDTO imagenDTO);
    void borrarImagen(long id);
    boolean existeImagen(long id);
    long contarImagenes();
    long contarImagenesPorTipo(String tipo);
    long contarImagenesPorProducto(long idProducto);    

}