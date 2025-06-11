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

import com.vegastore.jitarger.dto.base.CarritoDTO;
import com.vegastore.jitarger.dto.create.CreateCarritoDTO;
import com.vegastore.jitarger.dto.update.UpdateCarritoDTO;
import com.vegastore.jitarger.responce.ApiResponse;
import com.vegastore.jitarger.service.CarritoService;

@RestController
@RequestMapping("/api/carrito")
public class CarritoRestController {

    private final CarritoService carritoService;

    @Autowired
    public CarritoRestController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CarritoDTO>>> obtenerCarritos() {
        List<CarritoDTO> carritos = carritoService.obtenerCarritos();
        return ResponseEntity.ok(ApiResponse.success(carritos, "Carritos obtenidos correctamente"));
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<ApiResponse<List<CarritoDTO>>> obtenerCarritosPorUsuarioId(@PathVariable long id) {
        List<CarritoDTO> carritos = carritoService.obtenerCarritosPorUsuarioId(id);
        return ResponseEntity.ok(ApiResponse.success(carritos, "Carritos obtenidos correctamente"));
    }

    @GetMapping("/usaurio/{id}/estado/{estado}")
    public ResponseEntity<ApiResponse<CarritoDTO>> obtenerCarritoPorEstadoPorUsuarioId(@PathVariable long id, @PathVariable String estado) {
        CarritoDTO carrito = carritoService.obtenerCarritoPorEstadoPorUsuarioId(id, estado);
        return ResponseEntity.ok(ApiResponse.success(carrito, "Carrito obtenido correctamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CarritoDTO>> obtenerCarritoPorId(@PathVariable long id) {
        CarritoDTO carrito = carritoService.obtenerCarritoPorId(id);
        return ResponseEntity.ok(ApiResponse.success(carrito, "Carrito obtenido correctamente"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CarritoDTO>> crearCarrito(@RequestBody CreateCarritoDTO carritoDTO) {
        CarritoDTO carrito = carritoService.crearCarrito(carritoDTO);
        return new ResponseEntity<>(ApiResponse.success(carrito, "Carrito creado correctamente"), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> actualizarCarrito(@PathVariable long id, @RequestBody UpdateCarritoDTO carritoDTO) {
        carritoService.actualizarCarrito(id, carritoDTO);
        return ResponseEntity.ok(ApiResponse.success(null, "Carrito actualizado correctamente"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> borrarCarrito(@PathVariable long id) {
        carritoService.borrarCarrito(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Carrito borrado correctamente"));
    }

    @GetMapping("/existe/{id}")
    public ResponseEntity<ApiResponse<Boolean>> existeCarrito(@PathVariable long id) {
        Boolean existe = carritoService.existeCarrito(id);
        return ResponseEntity.ok(ApiResponse.success(existe, "Existe el carrito correctamente"));
    }
    
}
