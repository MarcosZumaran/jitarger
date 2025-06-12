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

import com.vegastore.jitarger.dto.base.SubCategoriaDTO;
import com.vegastore.jitarger.dto.create.CreateSubCategoriaDTO;
import com.vegastore.jitarger.dto.update.UpdateSubCategoriaDTO;
import com.vegastore.jitarger.responce.ApiResponse;
import com.vegastore.jitarger.service.SubCategoriaService;

@RestController
@RequestMapping("/api/subcategoria")
public class SubCategoriaRestController {
    
    private final SubCategoriaService subCategoriaService;

    @Autowired
    public SubCategoriaRestController(SubCategoriaService subCategoriaService) {
        this.subCategoriaService = subCategoriaService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SubCategoriaDTO>>> obtenerTodasLasSubcategorias(){
        List<SubCategoriaDTO> subcategorias = subCategoriaService.obtenerTodasLasSubcategorias();
        return ResponseEntity.ok(ApiResponse.success(subcategorias, "Subcategorias obtenidas correctamente"));
    }

    @GetMapping("/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<SubCategoriaDTO>>> obtenerSubcategorias(@PathVariable int pagina){
        List<SubCategoriaDTO> subcategorias = subCategoriaService.obtenerSubcategorias(pagina);
        return ResponseEntity.ok(ApiResponse.success(subcategorias, "Subcategorias obtenidas correctamente"));
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<ApiResponse<List<SubCategoriaDTO>>> obtenerSubcategoriasPorCategoria(@PathVariable long id){
        List<SubCategoriaDTO> subcategorias = subCategoriaService.obtenerSubcategoriasPorCategoria(id);
        return ResponseEntity.ok(ApiResponse.success(subcategorias, "Subcategorias obtenidas correctamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SubCategoriaDTO>> obtenerSubcategoriaPorId(@PathVariable long id){
        SubCategoriaDTO subcategoria = subCategoriaService.obtenerSubcategoriaPorId(id);
        return ResponseEntity.ok(ApiResponse.success(subcategoria, "Subcategoria obtenida correctamente"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SubCategoriaDTO>> crearSubcategoria(@RequestBody CreateSubCategoriaDTO subcategoria){
        SubCategoriaDTO subcategoriaCreada = subCategoriaService.crearSubCategoria(subcategoria);
        return new ResponseEntity<>(ApiResponse.success(subcategoriaCreada, "Subcategoria creada correctamente"), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> actualizarSubcategoria(@PathVariable long id, @RequestBody UpdateSubCategoriaDTO subcategoria){
        subCategoriaService.actualizarSubcategoria(id, subcategoria);
        return ResponseEntity.ok(ApiResponse.success(null, "Subcategoria actualizada correctamente"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> borrarSubcategoria(@PathVariable long id){
        subCategoriaService.borrarSubcategoria(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Subcategoria borrada correctamente"));
    }

    @GetMapping("/existe/{id}")
    public ResponseEntity<ApiResponse<Boolean>> existeSubcategoria(@PathVariable long id){
        boolean existe = subCategoriaService.existeSubcategoria(id);
        return ResponseEntity.ok(ApiResponse.success(existe, "Subcategoria obtenida correctamente"));
    }

}
