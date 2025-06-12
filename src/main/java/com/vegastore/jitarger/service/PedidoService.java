package com.vegastore.jitarger.service;

import java.util.List;

import com.vegastore.jitarger.dto.base.PedidoDTO;
import com.vegastore.jitarger.dto.create.CreatePedidoDTO;
import com.vegastore.jitarger.dto.update.UpdatePedidoDTO;

public interface PedidoService {

    List<PedidoDTO> obtenerTodosLosPedidos();

    List<PedidoDTO> obtenerPedidos(int pagina);

    List<PedidoDTO> obtenerPedidosPorUsuario(int pagina, long idUsuario);

    List<PedidoDTO> obtenerPedidosPorUsuarioPorEstado(int pagina, long idUsuario, String estado);

    List<PedidoDTO> obtenerPedidosPorEstado(int pagina, String estado);

    PedidoDTO obtenerPedidoPorId(long id);

    PedidoDTO crearPedido(CreatePedidoDTO pedidoDTO);

    void actualizarPedido(long id, UpdatePedidoDTO pedidoDTO);

    void borrarPedido(long id);

    boolean existePedido(long id);

    long contarPedidos();

}