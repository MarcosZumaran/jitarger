package com.vegastore.jitarger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vegastore.jitarger.dto.base.ProductoDTO;
import com.vegastore.jitarger.dto.create.CreateProductoDTO;
import com.vegastore.jitarger.dto.update.UpdateProductoDTO;
import com.vegastore.jitarger.responce.ApiResponse;
import com.vegastore.jitarger.service.ProductoService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/producto")
public class ProductoRestController {

    private final ProductoService productoService;
    
    @Autowired
    public ProductoRestController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductoDTO>>> obtenerTodosLosProductos() {
        List<ProductoDTO> productos = productoService.obtenerTodosLosProductos();
        return ResponseEntity.ok(ApiResponse.success(productos, "Productos obtenidos correctamente"));
    }

    @GetMapping("/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<ProductoDTO>>> obtenerProductos(@PathVariable int pagina) {
        List<ProductoDTO> productos = productoService.obtenerProductos(pagina);
        return ResponseEntity.ok(ApiResponse.success(productos, "Productos obtenidos correctamente"));
    }

    // Sin paginacion

    @GetMapping("categoria/{id}")
    public ResponseEntity<ApiResponse<List<ProductoDTO>>> obtenerTodosLosProductosPorCategoria(@PathVariable long id) {
        List<ProductoDTO> productos = productoService.obtenerTodosLosProductosPorCategoria(id);
        return ResponseEntity.ok(ApiResponse.success(productos, "Productos obtenidos correctamente"));
    }

    @GetMapping("/subcategoria/{id}")
    public ResponseEntity<ApiResponse<List<ProductoDTO>>> obtenerTodosLosProductosPorSubCategoria(@PathVariable long id) {
        List<ProductoDTO> productos = productoService.obtenerTodosLosProductosPorSubcategoria(id);
        return ResponseEntity.ok(ApiResponse.success(productos, "Productos obtenidos correctamente"));
    }

    @GetMapping("/marca/{marca}")
    public ResponseEntity<ApiResponse<List<ProductoDTO>>> obtenerTodosLosProductosPorMarca(@PathVariable String marca) {
        List<ProductoDTO> productos = productoService.obtenerTodosLosProductosPorMarca(marca);
        return ResponseEntity.ok(ApiResponse.success(productos, "Productos obtenidos correctamente"));
    }

    @GetMapping("/talla/{talla}")
    public ResponseEntity<ApiResponse<List<ProductoDTO>>> obtenerTodosLosProductosPorTalla(@PathVariable String talla) {
        List<ProductoDTO> productos = productoService.obtenerTodosLosProductosPorTalla(talla);
        return ResponseEntity.ok(ApiResponse.success(productos, "Productos obtenidos correctamente"));
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<ApiResponse<List<ProductoDTO>>> obtenerTodosLosProductosPorColor(@PathVariable String color) {
        List<ProductoDTO> productos = productoService.obtenerTodosLosProductosPorColor(color);
        return ResponseEntity.ok(ApiResponse.success(productos, "Productos obtenidos correctamente"));
    }

    @GetMapping("/genero/{genero}")
    public ResponseEntity<ApiResponse<List<ProductoDTO>>> obtenerTodosLosProductosPorGenero(@PathVariable String genero) {
        List<ProductoDTO> productos = productoService.obtenerTodosLosProductosPorGenero(genero);
        return ResponseEntity.ok(ApiResponse.success(productos, "Productos obtenidos correctamente"));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<ApiResponse<List<ProductoDTO>>> obtenerTodosLosProductosPorEstado(@PathVariable String estado) {
        List<ProductoDTO> productos = productoService.obtenerTodosLosProductosPorEstado(estado);
        return ResponseEntity.ok(ApiResponse.success(productos, "Productos obtenidos correctamente"));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<ApiResponse<List<ProductoDTO>>> obtenerTodosLosProductosPorNombre(@PathVariable String nombre) {
        List<ProductoDTO> productos = productoService.obtenerTodosLosProductosPorNombre(nombre);
        return ResponseEntity.ok(ApiResponse.success(productos, "Productos obtenidos correctamente"));
    }

    @GetMapping("/nombre-parcial/{nombre}")
    public ResponseEntity<ApiResponse<List<ProductoDTO>>> obtenerTodosLosProductosPorNombreParcial(@PathVariable String nombre) {
        List<ProductoDTO> productos = productoService.obetnerTodosLosProductosPorNombreParcial(nombre);
        return ResponseEntity.ok(ApiResponse.success(productos, "Productos obtenidos correctamente"));
    }

    // Con Paginacion

    @GetMapping("/categoria/{id}/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<ProductoDTO>>> obtenerProductosPorCategoria(@PathVariable long id, @PathVariable int pagina) {
        List<ProductoDTO> productos = productoService.obtenerProductosPorCategoria(pagina, id);
        return ResponseEntity.ok(ApiResponse.success(productos, "Productos obtenidos correctamente"));
    }

    @GetMapping("/subcategoria/{id}/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<ProductoDTO>>> obtenerProductosPorSubCategoria(@PathVariable long id, @PathVariable int pagina) {
        List<ProductoDTO> productos = productoService.obtenerProductosPorSubcategoria(pagina, id);
        return ResponseEntity.ok(ApiResponse.success(productos, "Productos obtenidos correctamente"));
    }

    @GetMapping("/marca/{marca}/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<ProductoDTO>>> obtenerProductosPorMarca(@PathVariable String marca, @PathVariable int pagina) {
        List<ProductoDTO> productos = productoService.obtenerProductosPorMarca(pagina, marca);
        return ResponseEntity.ok(ApiResponse.success(productos, "Productos obtenidos correctamente"));
    }

    @GetMapping("/talla/{talla}/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<ProductoDTO>>> obtenerProductosPorTalla(@PathVariable String talla, @PathVariable int pagina) {
        List<ProductoDTO> productos = productoService.obtenerProductosPorTalla(pagina, talla);
        return ResponseEntity.ok(ApiResponse.success(productos, "Productos obtenidos correctamente"));
    }

    @GetMapping("/color/{color}/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<ProductoDTO>>> obtenerProductosPorColor(@PathVariable String color, @PathVariable int pagina) {
        List<ProductoDTO> productos = productoService.obtenerProductosPorColor(pagina, color);
        return ResponseEntity.ok(ApiResponse.success(productos, "Productos obtenidos correctamente"));
    }

    @GetMapping("/genero/{genero}/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<ProductoDTO>>> obtenerProductosPorGenero(@PathVariable String genero, @PathVariable int pagina) {
        List<ProductoDTO> productos = productoService.obtenerProductosPorGenero(pagina, genero);
        return ResponseEntity.ok(ApiResponse.success(productos, "Productos obtenidos correctamente"));
    }

    @GetMapping("/estado/{estado}/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<ProductoDTO>>> obtenerProductosPorEstado(@PathVariable String estado, @PathVariable int pagina) {
        List<ProductoDTO> productos = productoService.obtenerProductosPorEstado(pagina, estado);
        return ResponseEntity.ok(ApiResponse.success(productos, "Productos obtenidos correctamente"));
    }

    @GetMapping("/nombre/{nombre}/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<ProductoDTO>>> obtenerProductosPorNombre(@PathVariable String nombre, @PathVariable int pagina) {
        List<ProductoDTO> productos = productoService.obtenerProductosPorNombre(pagina, nombre);
        return ResponseEntity.ok(ApiResponse.success(productos, "Productos obtenidos correctamente"));
    }

    @GetMapping("/nombre-parcial/{nombre}/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<ProductoDTO>>> obtenerProductosPorNombreParcial(@PathVariable String nombre, @PathVariable int pagina) {
        List<ProductoDTO> productos = productoService.obtenerProductosPorNombreParcial(pagina, nombre);
        return ResponseEntity.ok(ApiResponse.success(productos, "Productos obtenidos correctamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductoDTO>> obtenerProductoPorId(@PathVariable long id) {
        ProductoDTO producto = productoService.obtenerProductoPorId(id);
        return ResponseEntity.ok(ApiResponse.success(producto, "Producto obtenido correctamente"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductoDTO>> crearProducto(@RequestBody CreateProductoDTO productoDTO) {
        ProductoDTO producto = productoService.crearProducto(productoDTO);
        return new ResponseEntity<>(ApiResponse.success(producto, "Producto creado correctamente"), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> actualizarProducto(@PathVariable long id, @RequestBody UpdateProductoDTO productoDTO) {
        productoService.actualizarProducto(id, productoDTO);
        return ResponseEntity.ok(ApiResponse.success(null, "Producto actualizado correctamente"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> borrarProducto(@PathVariable long id) {
        productoService.borrarProducto(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Producto borrado correctamente"));
    }

    @GetMapping("/existe/{id}")
    public ResponseEntity<ApiResponse<Boolean>> existeProducto(@PathVariable long id) {
        boolean existe = productoService.existeProducto(id);
        return ResponseEntity.ok(ApiResponse.success(existe, "Producto obtenido correctamente"));
    }
    
}
