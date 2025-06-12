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

import com.vegastore.jitarger.dto.base.ProveedorDTO;
import com.vegastore.jitarger.dto.create.CreateProveedorDTO;
import com.vegastore.jitarger.dto.update.UpdateProveedorDTO;
import com.vegastore.jitarger.responce.ApiResponse;
import com.vegastore.jitarger.service.ProveedorService;


@RestController
@RequestMapping("/api/proveedor")
public class ProveedorRestController {
    
    private final ProveedorService proveedorService;

    @Autowired
    public ProveedorRestController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProveedorDTO>>> obtenerTodosLosProveedores() { 
        List<ProveedorDTO> proveedores = proveedorService.obtenerTodosLosProveedores();
        return ResponseEntity.ok(ApiResponse.success(proveedores, "Proveedores obtenidos correctamente"));
    }

    @GetMapping("/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<ProveedorDTO>>> obtenerProveedores(@PathVariable int pagina) {
        List<ProveedorDTO> proveedores = proveedorService.obtenerProveedores(pagina);
        return ResponseEntity.ok(ApiResponse.success(proveedores, "Proveedores obtenidos correctamente"));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<ApiResponse<List<ProveedorDTO>>> obtenerProveedoresPorNombre(@PathVariable String nombre) {
        List<ProveedorDTO> proveedores = proveedorService.obtenerProveedoresPorNombre(nombre);
        return ResponseEntity.ok(ApiResponse.success(proveedores, "Proveedores obtenidos correctamente"));
    }

    @GetMapping("/activo/{activo}")
    public ResponseEntity<ApiResponse<List<ProveedorDTO>>> obtenerProveedoresPorActivo(@PathVariable boolean activo) {
        List<ProveedorDTO> proveedores = proveedorService.obtenerProveedoresPorActivo(activo);
        return ResponseEntity.ok(ApiResponse.success(proveedores, "Proveedores obtenidos correctamente"));
    }

    @GetMapping("/ruc/{ruc}")
    public ResponseEntity<ApiResponse<ProveedorDTO>> obtenerProveedorPorRuc(@PathVariable String ruc) {
        ProveedorDTO proveedor = proveedorService.obtenerProveedorPorRuc(ruc);
        return ResponseEntity.ok(ApiResponse.success(proveedor, "Proveedor obtenido correctamente"));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<ProveedorDTO>> obtenerProveedorPorId(@PathVariable long id) {
        ProveedorDTO proveedor = proveedorService.obtenerProveedorPorId(id);
        return ResponseEntity.ok(ApiResponse.success(proveedor, "Proveedor obtenido correctamente"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProveedorDTO>> crearProveedor(@RequestBody CreateProveedorDTO proveedorDTO) {
        ProveedorDTO proveedor = proveedorService.crearProveedor(proveedorDTO);
        return new ResponseEntity<>(ApiResponse.success(proveedor, "Proveedor creado correctamente"), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> actualizarProveedor(@PathVariable long id, @RequestBody UpdateProveedorDTO proveedorDTO) {
        proveedorService.actualizarProveedor(id, proveedorDTO);
        return ResponseEntity.ok(ApiResponse.success(null, "Proveedor actualizado correctamente"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> borrarProveedor(@PathVariable long id) {
        proveedorService.borrarProveedor(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Proveedor borrado correctamente"));
    }

    @GetMapping("/existe/{id}")
    public ResponseEntity<ApiResponse<Boolean>> existeProveedor(@PathVariable long id) {
        boolean existe = proveedorService.existeProveedor(id);
        return ResponseEntity.ok(ApiResponse.success(existe, "Proveedor obtenido correctamente"));
    }

}
