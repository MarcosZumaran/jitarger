package com.vegastore.jitarger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vegastore.jitarger.dto.base.PedidoDTO;
import com.vegastore.jitarger.dto.create.CreatePedidoDTO;
import com.vegastore.jitarger.dto.update.UpdatePedidoDTO;
import com.vegastore.jitarger.responce.ApiResponse;
import com.vegastore.jitarger.service.PedidoService;

@RestController
@RequestMapping("/api/pedido")
public class PedidoRestController {

    private final PedidoService pedidoService;
    
    @Autowired
    public PedidoRestController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PedidoDTO>>> obtenerTodosLosPedidos() {
        List<PedidoDTO> pedidos = pedidoService.obtenerTodosLosPedidos();
        return ResponseEntity.ok(ApiResponse.success(pedidos, "Pedidos obtenidos correctamente"));
    }

    @GetMapping("/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<PedidoDTO>>> obtenerPedidos(@PathVariable int pagina) {
        List<PedidoDTO> pedidos = pedidoService.obtenerPedidos(pagina);
        return ResponseEntity.ok(ApiResponse.success(pedidos, "Pedidos obtenidos correctamente"));
    }

    @GetMapping("/usuario/{id}/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<PedidoDTO>>> obtenerPedidosPorUsuario(@PathVariable long id, @PathVariable int pagina) {
        List<PedidoDTO> pedidos = pedidoService.obtenerPedidosPorUsuario(pagina, id);
        return ResponseEntity.ok(ApiResponse.success(pedidos, "Pedidos obtenidos correctamente"));
    }

    @GetMapping("/usaurio/{id}/estado/{estado}/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<PedidoDTO>>> obtenerPedidosPorUsuarioPorEstado(@PathVariable long id, @PathVariable String estado, @PathVariable int pagina) {
        List<PedidoDTO> pedidos = pedidoService.obtenerPedidosPorUsuarioPorEstado(pagina, id, estado);
        return ResponseEntity.ok(ApiResponse.success(pedidos, "Pedidos obtenidos correctamente"));
    }

    @GetMapping("/estado/{estado}/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<PedidoDTO>>> obtenerPedidosPorEstado(@PathVariable String estado, @PathVariable int pagina) {
        List<PedidoDTO> pedidos = pedidoService.obtenerPedidosPorEstado(pagina, estado);
        return ResponseEntity.ok(ApiResponse.success(pedidos, "Pedidos obtenidos correctamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PedidoDTO>> obtenerPedidoPorId(@PathVariable long id) {
        PedidoDTO pedido = pedidoService.obtenerPedidoPorId(id);
        return ResponseEntity.ok(ApiResponse.success(pedido, "Pedido obtenido correctamente"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PedidoDTO>> crearPedido(@RequestBody CreatePedidoDTO pedidoDTO) {
        PedidoDTO pedido = pedidoService.crearPedido(pedidoDTO);
        return ResponseEntity.ok(ApiResponse.success(pedido, "Pedido creado correctamente"));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> actualizarPedido(@PathVariable long id, @RequestBody UpdatePedidoDTO pedidoDTO) {
        pedidoService.actualizarPedido(id, pedidoDTO);
        return ResponseEntity.ok(ApiResponse.success(null, "Pedido actualizado correctamente"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> borrarPedido(@PathVariable long id) {
        pedidoService.borrarPedido(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Pedido borrado correctamente"));
    }

    @GetMapping("/existe/{id}")
    public ResponseEntity<ApiResponse<Boolean>> existePedido(@PathVariable long id) {
        boolean existe = pedidoService.existePedido(id);
        return ResponseEntity.ok(ApiResponse.success(existe, "Pedido obtenido correctamente"));
    }

    @GetMapping("/contar")
    public ResponseEntity<ApiResponse<Long>> contarPedidos() {
        Long cantidad = pedidoService.contarPedidos();
        return ResponseEntity.ok(ApiResponse.success(cantidad, "Cantidad de pedidos obtenida correctamente"));
    }
    
}
