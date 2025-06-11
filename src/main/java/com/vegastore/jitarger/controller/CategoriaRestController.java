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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vegastore.jitarger.dto.base.CategoriaDTO;
import com.vegastore.jitarger.dto.create.CreateCategoriaDTO;
import com.vegastore.jitarger.dto.update.UpdateCategoriaDTO;
import com.vegastore.jitarger.responce.ApiResponse;
import com.vegastore.jitarger.service.CategoriaService;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaRestController {

    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaRestController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoriaDTO>>> obtenerCategorias() {
        List<CategoriaDTO> categorias = categoriaService.obtenerTodasLasCategorias();
        return ResponseEntity.ok(ApiResponse.success(categorias, "Categorias obtenidas correctamente"));
    }

    @GetMapping("/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<CategoriaDTO>>> obtenerCategoriasPorPagina(@PathVariable int pagina) {
        List<CategoriaDTO> categorias = categoriaService.obtenerCategorias(pagina);
        return ResponseEntity.ok(ApiResponse.success(categorias, "Categorias obtenidas correctamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoriaDTO>> obtenerCategoriaPorId(@PathVariable long id) {
        CategoriaDTO categoria = categoriaService.obtenerCategoriaPorId(id);
        return ResponseEntity.ok(ApiResponse.success(categoria, "Categoria obtenida correctamente"));
    }

    @GetMapping("/producto/{id}")
    public ResponseEntity<ApiResponse<CategoriaDTO>> obtenerCategoriaPorProducto(@PathVariable long id) {
        CategoriaDTO categoria = categoriaService.obtenerCategoriaPorProducto(id);
        return ResponseEntity.ok(ApiResponse.success(categoria, "Categoria obtenida correctamente"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoriaDTO>> crearCategoria(@RequestBody CreateCategoriaDTO categoriaDTO) {
        CategoriaDTO categoria = categoriaService.crearCategoria(categoriaDTO);
        return new ResponseEntity<>(ApiResponse.success(categoria, "Categoria creada correctamente"), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> actualizarCategoria(@PathVariable long id, @RequestBody UpdateCategoriaDTO categoriaDTO) {
        categoriaService.actualizarCategoria(id, categoriaDTO);
        return ResponseEntity.ok(ApiResponse.success(null, "Categoria actualizada correctamente"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> borrarCategoria(@PathVariable long id) {
        categoriaService.borrarCategoria(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Categoria eliminada exitosamente."));
    }

    @GetMapping("/existe/{id}")
    public ResponseEntity<ApiResponse<Boolean>> existeCategoria(@PathVariable long id) {
        Boolean existe = categoriaService.existeCategoria(id);
        return ResponseEntity.ok(ApiResponse.success(existe, "Existe la categoria correctamente"));
    }
    
}
