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

import com.vegastore.jitarger.dto.base.ProductoImagenDTO;
import com.vegastore.jitarger.dto.create.CreateProductoImagenDTO;
import com.vegastore.jitarger.dto.update.UpdateProductoImagenDTO;
import com.vegastore.jitarger.responce.ApiResponse;
import com.vegastore.jitarger.service.ProductoImagenService;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/producto-imagen")
public class ProductoImagenRestController {

    private final ProductoImagenService productoImagenService;

    @Autowired
    public ProductoImagenRestController(ProductoImagenService productoImagenService) {
        this.productoImagenService = productoImagenService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductoImagenDTO>>> obtenerTodasLasImagenes() {
        List<ProductoImagenDTO> imagenes = productoImagenService.obtenerTodasLasImagenes();
        return ResponseEntity.ok(ApiResponse.success(imagenes, "Imagenes obtenidas correctamente"));
    }

    @GetMapping("/producto/{id}")
    public ResponseEntity<ApiResponse<List<ProductoImagenDTO>>> obtenerImagenesPorProducto(@PathVariable long id) {
        List<ProductoImagenDTO> imagenes = productoImagenService.obtenerImagenesPorProducto(id);
        return ResponseEntity.ok(ApiResponse.success(imagenes, "Imagenes obtenidas correctamente"));
    }

    @GetMapping("/producto/{id}/tipo/{tipo}")
    public ResponseEntity<ApiResponse<List<ProductoImagenDTO>>> obtenerImagenesPorProductoYTipo(@PathVariable long id, @PathVariable String tipo) {
        List<ProductoImagenDTO> imagenes = productoImagenService.obtenerImagenesPorProductoYTipo(id, tipo);
        return ResponseEntity.ok(ApiResponse.success(imagenes, "Imagenes obtenidas correctamente"));
    }

    @GetMapping("/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<ProductoImagenDTO>>> obtenerImagenes(@PathVariable int pagina) {
        List<ProductoImagenDTO> imagenes = productoImagenService.obtenerImagenes(pagina);
        return ResponseEntity.ok(ApiResponse.success(imagenes, "Imagenes obtenidas correctamente"));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<ApiResponse<List<ProductoImagenDTO>>> obtenerTodasLasImagenesPorTipo(@PathVariable String tipo) {
        List<ProductoImagenDTO> imagenes = productoImagenService.obtenerTodasLasImagenesPorTipo(tipo);
        return ResponseEntity.ok(ApiResponse.success(imagenes, "Imagenes obtenidas correctamente"));
    }

    @GetMapping("/tipo/{tipo}/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<ProductoImagenDTO>>> obtenerImagenesPorTipo(@PathVariable int pagina, @PathVariable String tipo) {
        List<ProductoImagenDTO> imagenes = productoImagenService.obtenerImagenesPorTipo(pagina, tipo);
        return ResponseEntity.ok(ApiResponse.success(imagenes, "Imagenes obtenidas correctamente"));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<ProductoImagenDTO>> obtenerImagenPorId(@PathVariable long id) {
        ProductoImagenDTO imagen = productoImagenService.obtenerImagenPorId(id);
        return ResponseEntity.ok(ApiResponse.success(imagen, "Imagen obtenida correctamente"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductoImagenDTO>> crearImagen(@RequestBody CreateProductoImagenDTO imagen) {
        ProductoImagenDTO imagenCreada = productoImagenService.crearImagen(imagen);
        return new ResponseEntity<>(ApiResponse.success(imagenCreada, "Imagen creada correctamente"), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> actualizarImagen(@PathVariable long id, @RequestBody UpdateProductoImagenDTO imagen) {
        productoImagenService.actualizarImagen(id, imagen);
        return ResponseEntity.ok(ApiResponse.success(null, "Imagen actualizada correctamente"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> borrarImagen(@PathVariable long id) {
        productoImagenService.borrarImagen(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Imagen borrada correctamente"));
    }

    @GetMapping("/existe/{id}")
    public ResponseEntity<ApiResponse<Boolean>> existeImagen(@PathVariable long id) {
        boolean existe = productoImagenService.existeImagen(id);
        return ResponseEntity.ok(ApiResponse.success(existe, "Imagen obtenida correctamente"));
    }

    @GetMapping("/contar")
    public ResponseEntity<ApiResponse<Long>> contarImagenes() {
        Long cantidad = productoImagenService.contarImagenes();
        return ResponseEntity.ok(ApiResponse.success(cantidad, "Cantidad de imagenes obtenida correctamente"));
    }

    @GetMapping("/contar/tipo/{tipo}")
    public ResponseEntity<ApiResponse<Long>> contarImagenesPorTipo(@PathVariable String tipo) {
        Long cantidad = productoImagenService.contarImagenesPorTipo(tipo);
        return ResponseEntity.ok(ApiResponse.success(cantidad, "Cantidad de imagenes obtenida correctamente"));
    }

    @GetMapping("/contar/producto/{id}")
    public ResponseEntity<ApiResponse<Long>> contarImagenesPorProducto(@PathVariable long id) {
        Long cantidad = productoImagenService.contarImagenesPorProducto(id);
        return ResponseEntity.ok(ApiResponse.success(cantidad, "Cantidad de imagenes obtenida correctamente"));
    }
    
}
