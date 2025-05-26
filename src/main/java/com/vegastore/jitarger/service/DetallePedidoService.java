package com.vegastore.jitarger.service;

import java.util.List;

import com.vegastore.jitarger.dto.base.DetallePedidoDTO;
import com.vegastore.jitarger.dto.create.CreateDetallePedidoDTO;
import com.vegastore.jitarger.dto.update.UpdateDetallePedidoDTO;

public interface DetallePedidoService {
    List<DetallePedidoDTO> obtenerDetallesPedidoPorPedido(long pedidoId);
    DetallePedidoDTO obtenerDetallePedidoPorId(long id);
    long crearDetallePedido(CreateDetallePedidoDTO detallePedidoDTO);
    void actualizarDetallePedido(long id, UpdateDetallePedidoDTO detallePedidoDTO);
    void borrarDetallePedido(long id);
    boolean existeDetallePedido(long id);
}
