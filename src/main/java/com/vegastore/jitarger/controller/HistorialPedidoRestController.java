package com.vegastore.jitarger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vegastore.jitarger.dto.base.HistorialPedidoDTO;
import com.vegastore.jitarger.dto.create.CreateHistorialPedidoDTO;
import com.vegastore.jitarger.responce.ApiResponse;
import com.vegastore.jitarger.service.HistorialPedidoService;

// la creacion de historiales de pedido
// tambien puede hacerce mediante triggers desde la base de datos, 
// aun asi, la creacion de historiales de pedido se puede realizar desde la api

@RestController
@RequestMapping("/api/historial-pedido")
public class HistorialPedidoRestController {

    private final HistorialPedidoService historialPedidoService;

    @Autowired
    public HistorialPedidoRestController(HistorialPedidoService historialPedidoService) {
        this.historialPedidoService = historialPedidoService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<HistorialPedidoDTO>>> obtenerHistorialPedidos() {
        List<HistorialPedidoDTO> historialPedidos = historialPedidoService.obtenerHistorialPedidos();
        return ResponseEntity.ok(ApiResponse.success(historialPedidos, "HistorialPedidos obtenidos correctamente"));
    }

    @GetMapping("/usuario/{id}/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<HistorialPedidoDTO>>> obtenerHistorialPedidosPorUsuario(@PathVariable long id ,@PathVariable int pagina) {
        List<HistorialPedidoDTO> historialPedidos = historialPedidoService.obtenerHistorialPedidoPorUsuario(pagina, id);
        return ResponseEntity.ok(ApiResponse.success(historialPedidos, "HistorialPedidos obtenidos correctamente"));
    }

    @GetMapping("pedido/{id}/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<HistorialPedidoDTO>>> obtenerHistorialPedidosPorPedido(@PathVariable long id ,@PathVariable int pagina) {
        List<HistorialPedidoDTO> historialPedidos = historialPedidoService.obtenerHistorialPedidoPorPedido(pagina, id);
        return ResponseEntity.ok(ApiResponse.success(historialPedidos, "HistorialPedidos obtenidos correctamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HistorialPedidoDTO>> obtenerHistorialPedidoPorId(@PathVariable long id) {
        HistorialPedidoDTO historialPedido = historialPedidoService.obtenerHistorialPedidoPorId(id);
        return ResponseEntity.ok(ApiResponse.success(historialPedido, "HistorialPedido obtenido correctamente"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<HistorialPedidoDTO>> crearHistorialPedido(@RequestBody CreateHistorialPedidoDTO historialPedidoDTO) {
        HistorialPedidoDTO historialPedido = historialPedidoService.crearHistorialPedido(historialPedidoDTO);
        return new ResponseEntity<>(ApiResponse.success(historialPedido, "HistorialPedido creado correctamente"), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> borrarHistorialPedido(@PathVariable long id) {
        historialPedidoService.borrarHistorialPedido(id);
        return ResponseEntity.ok(ApiResponse.success(null, "HistorialPedido borrado correctamente"));
    }

    @GetMapping("/existe/{id}")
    public ResponseEntity<ApiResponse<Boolean>> existeHistorialPedido(@PathVariable long id) {
        Boolean existe = historialPedidoService.existeHistorialPedido(id);
        return ResponseEntity.ok(ApiResponse.success(existe, "Existe el historialPedido correctamente"));
    }

}
