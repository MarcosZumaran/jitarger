package com.vegastore.jitarger.service;

import java.util.List;

import com.vegastore.jitarger.dto.base.ProductoDTO;
import com.vegastore.jitarger.dto.create.CreateProductoDTO;
import com.vegastore.jitarger.dto.update.UpdateProductoDTO;

public interface ProductoService {

    List<ProductoDTO> obtenerProductos();
    List<ProductoDTO> obtenerProductosPorCategoria(long idCategoria);
    List<ProductoDTO> obtenerProductosPorSubcategoria(long idSubcategoria);
    List<ProductoDTO> obtenerProductosPorMarca(String marca);
    List<ProductoDTO> obtenerProductosPorTalla(String talla);
    List<ProductoDTO> obtenerProductosPorColor(String color);
    List<ProductoDTO> obtenerProductosPorGenero(String genero);
    List<ProductoDTO> obtenerProductosPorEstado(String estado);
    List<ProductoDTO> buscarProductosPorNombre(String nombre);
    ProductoDTO obtenerProductoPorId(long id);
    long crearProducto(CreateProductoDTO productoDTO);
    void actualizarProducto(long id, UpdateProductoDTO productoDTO);
    void borrarProducto(long id);
    boolean existeProducto(long id);
    
}