package com.vegastore.jitarger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vegastore.jitarger.dto.base.CarritoDTO;
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
    
}
