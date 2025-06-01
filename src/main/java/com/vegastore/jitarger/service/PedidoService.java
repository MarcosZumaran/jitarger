package com.vegastore.jitarger.service;

import java.util.List;

import com.vegastore.jitarger.dto.base.PedidoDTO;
import com.vegastore.jitarger.dto.create.CreatePedidoDTO;
import com.vegastore.jitarger.dto.update.UpdatePedidoDTO;

public interface PedidoService {

    List<PedidoDTO> obtenerPedidos();
    List<PedidoDTO> obtenerPedidosPorUsuario(long idUsuario);
    List<PedidoDTO> obtenerPedidosPorEstado(String estado);
    PedidoDTO obtenerPedidoPorId(long id);
    PedidoDTO crearPedido(CreatePedidoDTO pedidoDTO);
    void actualizarPedido(long id, UpdatePedidoDTO pedidoDTO);
    void actualizarEstadoPedido(long id, String nuevoEstado, String comentario);
    void cancelarPedido(long id, String comentario);
    void borrarPedido(long id);
    boolean existePedido(long id);
    
}