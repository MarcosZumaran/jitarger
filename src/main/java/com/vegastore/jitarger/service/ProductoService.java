package com.vegastore.jitarger.service;

import java.util.List;

import com.vegastore.jitarger.dto.base.ProductoDTO;
import com.vegastore.jitarger.dto.create.CreateProductoDTO;
import com.vegastore.jitarger.dto.update.UpdateProductoDTO;

public interface ProductoService {

    List<ProductoDTO> obtenerTodosLosProductos();
    List<ProductoDTO> obtenerProductos(int pagina);
    List<ProductoDTO> obtenerTodosLosProductosPorCategoria(long idCategoria);
    List<ProductoDTO> obtenerTodosLosProductosPorSubcategoria(long idSubcategoria);
    List<ProductoDTO> obtenerTodosLosProductosPorMarca(String marca);
    List<ProductoDTO> obtenerTodosLosProductosPorTalla(String talla);
    List<ProductoDTO> obtenerTodosLosProductosPorColor(String color);
    List<ProductoDTO> obtenerTodosLosProductosPorGenero(String genero);
    List<ProductoDTO> obtenerTodosLosProductosPorEstado(String estado);
    List<ProductoDTO> obtenerTodosLosProductosPorNombre(String nombre);
    List<ProductoDTO> obetnerTodosLosProductosPorNombreParcial(String nombre);
    List<ProductoDTO> obtenerProductosPorMarca(int pagina, String marca);
    List<ProductoDTO> obtenerProductosPorTalla(int pagina, String talla);
    List<ProductoDTO> obtenerProductosPorColor(int pagina, String color);
    List<ProductoDTO> obtenerProductosPorGenero(int pagina, String genero);
    List<ProductoDTO> obtenerProductosPorEstado(int pagina, String estado);
    List<ProductoDTO> obtenerProductosPorNombre(int pagina, String nombre);
    List<ProductoDTO> obtenerProductosPorNombreParcial(int pagina, String nombre);
    ProductoDTO obtenerProductoPorId(long id);
    ProductoDTO crearProducto(CreateProductoDTO productoDTO);
    void actualizarProducto(long id, UpdateProductoDTO productoDTO);
    void borrarProducto(long id);
    boolean existeProducto(long id);
}