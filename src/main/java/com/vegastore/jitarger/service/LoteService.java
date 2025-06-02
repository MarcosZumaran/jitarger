package com.vegastore.jitarger.service;

import java.util.List;

import com.vegastore.jitarger.dto.base.LoteDTO;
import com.vegastore.jitarger.dto.create.CreateLoteDTO;
import com.vegastore.jitarger.dto.update.UpdateLoteDTO;

public interface LoteService {

    List<LoteDTO> obtenerTodosLosLotes();
    List<LoteDTO> obtenerLotes(int pagina);
    List<LoteDTO> obtenerLotesPorProducto(int pag, long idProducto);
    List<LoteDTO> obtenerLotesPorProveedor(int pag, long idProveedor);
    LoteDTO obtenerLotePorId(long id);
    LoteDTO crearLote(CreateLoteDTO loteDTO);
    void actualizarLote(long id, UpdateLoteDTO loteDTO);
    void borrarLote(long id);
    boolean existeLote(long id);
    
}
