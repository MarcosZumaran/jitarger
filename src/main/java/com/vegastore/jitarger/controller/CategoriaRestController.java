package com.vegastore.jitarger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vegastore.jitarger.dto.base.CategoriaDTO;
import com.vegastore.jitarger.dto.create.CreateCategoriaDTO;
import com.vegastore.jitarger.dto.update.UpdateCategoriaDTO;
import com.vegastore.jitarger.responce.ApiResponse;
import com.vegastore.jitarger.service.CategoriaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaRestController {
    
    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaRestController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // Aquí puedes agregar métodos para manejar las solicitudes HTTP relacionadas con categorías

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoriaDTO>>> obtenerTodasLasCategorias(){
        List<CategoriaDTO> categorias = categoriaService.obtenerCategorias();
        return ResponseEntity.ok(ApiResponse.success(categorias, "Lista de categorías obtenida correctamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoriaDTO>> obtenerCategoriaPorId(@PathVariable Long id) {
        CategoriaDTO categoria = categoriaService.obtenerCategoriaPorId(id);
        return ResponseEntity.ok(ApiResponse.success(categoria, "Categoría obtenida correctamente"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoriaDTO>> crearCategoria(@Valid @RequestBody CreateCategoriaDTO categoriaDTO) {
        CategoriaDTO nuevaCategoria = categoriaService.crearCategoria(categoriaDTO);
        return new ResponseEntity<>(
            ApiResponse.success(nuevaCategoria, "Categoría creada correctamente"),
            HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> actualizarCategoria(
            @PathVariable Long id, 
            @Valid @RequestBody UpdateCategoriaDTO categoriaDTO) {
        categoriaService.actualizarCategoria(id, categoriaDTO);
        return ResponseEntity.ok(ApiResponse.success(null, "Categoría actualizada correctamente"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarCategoria(@PathVariable Long id) {
        categoriaService.borrarCategoria(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Categoría eliminada correctamente"));
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Integer>> contarCategorias() {
        int totalCategorias = categoriaService.contarCategorias();
        return ResponseEntity.ok(ApiResponse.success(totalCategorias, "Total de categorías obtenidas correctamente"));
    }

}
