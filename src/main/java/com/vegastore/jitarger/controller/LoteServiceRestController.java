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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vegastore.jitarger.dto.base.LoteDTO;
import com.vegastore.jitarger.dto.create.CreateLoteDTO;
import com.vegastore.jitarger.dto.update.UpdateLoteDTO;
import com.vegastore.jitarger.responce.ApiResponse;
import com.vegastore.jitarger.service.LoteService;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/lote")
public class LoteServiceRestController {

    private final LoteService loteService;

    @Autowired
    public LoteServiceRestController(LoteService loteService) {
        this.loteService = loteService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LoteDTO>>> obtenerTodosLosLotes() {
        List<LoteDTO> lotes = loteService.obtenerTodosLosLotes();
        return ResponseEntity.ok(ApiResponse.success(lotes, "Lotes obtenidos correctamente"));
    }

    @GetMapping("/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<LoteDTO>>> obtenerTodosLosLotes(@PathVariable int pagina) {
        List<LoteDTO> lotes = loteService.obtenerLotes(pagina);
        return ResponseEntity.ok(ApiResponse.success(lotes, "Lotes obtenidos correctamente"));
    }

    @GetMapping("/producto/{id}/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<LoteDTO>>> obtenerLotesPorProducto(@PathVariable long id, @PathVariable int pagina) {
        List<LoteDTO> lotes = loteService.obtenerLotesPorProducto(pagina, id);
        return ResponseEntity.ok(ApiResponse.success(lotes, "Lotes obtenidos correctamente"));
    }

    @GetMapping("/proveedor/{id}/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<LoteDTO>>> obtenerLotesPorProveedor(@PathVariable long id, @PathVariable int pagina) {
        List<LoteDTO> lotes = loteService.obtenerLotesPorProveedor(pagina, id);
        return ResponseEntity.ok(ApiResponse.success(lotes, "Lotes obtenidos correctamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LoteDTO>> obtenerLotePorId(@PathVariable long id) {
        LoteDTO lote = loteService.obtenerLotePorId(id);
        return ResponseEntity.ok(ApiResponse.success(lote, "Lote obtenido correctamente"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<LoteDTO>> crearLote(@RequestBody CreateLoteDTO loteDTO) {
        LoteDTO lote = loteService.crearLote(loteDTO);
        return new ResponseEntity<>(ApiResponse.success(lote, "Lote creado correctamente"), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> actualizarLote(@PathVariable long id, @RequestBody UpdateLoteDTO loteDTO) {
        loteService.actualizarLote(id, loteDTO);
        return ResponseEntity.ok(ApiResponse.success(null, "Lote actualizado correctamente"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarLote(@PathVariable long id) {
        loteService.eliminarLote(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Lote borrado correctamente"));
    }

    @GetMapping("/existe/{id}")
    public ResponseEntity<ApiResponse<Boolean>> existeLote(@PathVariable long id) {
        Boolean existe = loteService.existeLote(id);
        return ResponseEntity.ok(ApiResponse.success(existe, "Existe el lote correctamente"));
    }
    
}
