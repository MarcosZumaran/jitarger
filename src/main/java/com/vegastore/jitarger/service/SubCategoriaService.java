package com.vegastore.jitarger.service;

import java.util.List;

import com.vegastore.jitarger.dto.base.SubCategoriaDTO;
import com.vegastore.jitarger.dto.create.CreateSubCategoriaDTO;
import com.vegastore.jitarger.dto.update.UpdateSubCategoriaDTO;

public interface SubCategoriaService {

    List<SubCategoriaDTO> obtenerSubcategorias(int pagina);
    List<SubCategoriaDTO> obtenerTodasLasSubcategorias();
    List<SubCategoriaDTO> obtenerSubcategoriasPorCategoria(long idCategoria);
    SubCategoriaDTO obtenerSubcategoriaPorId(long id);
    SubCategoriaDTO obtenerSubCategoriaPorProducto(long idProducto);
    SubCategoriaDTO crearSubCategoria(CreateSubCategoriaDTO subcategoriaDTO);
    void actualizarSubcategoria(long id, UpdateSubCategoriaDTO subcategoriaDTO);
    void borrarSubcategoria(long id);
    boolean existeSubcategoria(long id);
    
}