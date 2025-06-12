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

import com.vegastore.jitarger.dto.base.DetallePedidoDTO;
import com.vegastore.jitarger.dto.create.CreateDetallePedidoDTO;
import com.vegastore.jitarger.dto.update.UpdateDetallePedidoDTO;
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

    @GetMapping("pedido/{id}/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<DetallePedidoDTO>>> obtenerDetallesPedidoPorPedido(@PathVariable long id ,@PathVariable int pagina) {
        List<DetallePedidoDTO> detallePedidos = detallePedidoService.obtenerDetallesPedidoPorPedido(pagina, id);
        return ResponseEntity.ok(ApiResponse.success(detallePedidos, "DetallePedidos obtenidos correctamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DetallePedidoDTO>> obtenerDetallePedidoPorId(@PathVariable long id) {
        DetallePedidoDTO detallePedido = detallePedidoService.obtenerDetallePedidoPorId(id);
        return ResponseEntity.ok(ApiResponse.success(detallePedido, "DetallePedido obtenido correctamente"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DetallePedidoDTO>> crearDetallePedido(@RequestBody CreateDetallePedidoDTO detallePedidoDTO) {
        DetallePedidoDTO detallePedido = detallePedidoService.crearDetallePedido(detallePedidoDTO);
        return new ResponseEntity<>(ApiResponse.success(detallePedido, "DetallePedido creado correctamente"), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> actualizarDetallePedido(@PathVariable long id, @RequestBody UpdateDetallePedidoDTO detallePedidoDTO) {
        detallePedidoService.actualizarDetallePedido(id, detallePedidoDTO);
        return ResponseEntity.ok(ApiResponse.success(null, "DetallePedido actualizado correctamente"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> borrarDetallePedido(@PathVariable long id) {
        detallePedidoService.borrarDetallePedido(id);
        return ResponseEntity.ok(ApiResponse.success(null, "DetallePedido borrado correctamente"));
    }

    @GetMapping("/existe/{id}")
    public ResponseEntity<ApiResponse<Boolean>> existeDetallePedido(@PathVariable long id) {
        Boolean existe = detallePedidoService.existeDetallePedido(id);
        return ResponseEntity.ok(ApiResponse.success(existe, "Existe el detallePedido correctamente"));
    }
    
}
