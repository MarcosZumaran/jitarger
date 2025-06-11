package com.vegastore.jitarger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vegastore.jitarger.dto.base.DetallePedidoDTO;
import com.vegastore.jitarger.responce.ApiResponse;
import com.vegastore.jitarger.service.DetallePedidoService;

@RestController
@RequestMapping("/api/detalle-pedido")
public class DetallePedidoRestController {

    private final DetallePedidoService detallePedidoService;
    
    @Autowired
    public DetallePedidoRestController(DetallePedidoService detallePedidoService) {
        this.detallePedidoService = detallePedidoService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DetallePedidoDTO>>> obtenerDetallePedidos() {
        List<DetallePedidoDTO> detallePedidos = detallePedidoService.obtenerDetallePedidos();
        return ResponseEntity.ok(ApiResponse.success(detallePedidos, "DetallePedidos obtenidos correctamente"));
    }
    
}
