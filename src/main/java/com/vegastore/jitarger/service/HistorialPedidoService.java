package com.vegastore.jitarger.service;

import java.util.List;

import com.vegastore.jitarger.dto.base.HistorialPedidoDTO;
import com.vegastore.jitarger.dto.create.CreateHistorialPedidoDTO;

public interface HistorialPedidoService {
    List<HistorialPedidoDTO> obtenerHistorialPedidoPorUsuario(long idUsuario);
    List<HistorialPedidoDTO> obtenerHistorialPedidoPorPedido(long idPedido);
    HistorialPedidoDTO obtenerHistorialPedidoPorId(long id);
    HistorialPedidoDTO crearHistorialPedido(CreateHistorialPedidoDTO historialPedidoDTO);
    void borrarHistorialPedido(long id);
    boolean existeHistorialPedido(long id);
}
