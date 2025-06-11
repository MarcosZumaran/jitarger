package com.vegastore.jitarger.service;

import java.util.List;

import com.vegastore.jitarger.dto.base.CategoriaDTO;
import com.vegastore.jitarger.dto.create.CreateCategoriaDTO;
import com.vegastore.jitarger.dto.update.UpdateCategoriaDTO;

public interface CategoriaService {

    List<CategoriaDTO> obtenerTodasLasCategorias();
    List<CategoriaDTO> obtenerCategorias(int pagina);
    CategoriaDTO obtenerCategoriaPorId(long id);
    CategoriaDTO obtenerCategoriaPorProducto(long idProducto);
    CategoriaDTO crearCategoria(CreateCategoriaDTO categoriaDTO);
    void actualizarCategoria(long id, UpdateCategoriaDTO categoriaDTO);
    void borrarCategoria(long id);
    boolean existeCategoria(long id);
}
