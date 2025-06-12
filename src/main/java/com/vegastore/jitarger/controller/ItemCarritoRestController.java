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

import com.vegastore.jitarger.dto.base.ItemCarritoDTO;
import com.vegastore.jitarger.dto.create.CreateItemCarritoDTO;
import com.vegastore.jitarger.dto.update.UpdateItemCarritoDTO;
import com.vegastore.jitarger.responce.ApiResponse;
import com.vegastore.jitarger.service.ItemCarritoService;

@RestController
@RequestMapping("/api/itemcarrito")
public class ItemCarritoRestController {

    private final ItemCarritoService itemCarritoService;

    @Autowired
    public ItemCarritoRestController(ItemCarritoService itemCarritoService) {
        this.itemCarritoService = itemCarritoService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ItemCarritoDTO>>> obtenerTodosLosItemsCarrito(){
        List<ItemCarritoDTO> itemsCarrito = itemCarritoService.obtenerTodosLosItemsCarrito();
        return ResponseEntity.ok(ApiResponse.success(itemsCarrito, "ItemsCarrito obtenidos correctamente"));
    }

    @GetMapping("/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<ItemCarritoDTO>>> obtenerItemsCarrito(@PathVariable int pagina){
        List<ItemCarritoDTO> itemsCarrito = itemCarritoService.obtenerItemsCarrito(pagina);
        return ResponseEntity.ok(ApiResponse.success(itemsCarrito, "ItemsCarrito obtenidos correctamente"));
    }

    @GetMapping("/carrito/{id}/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<ItemCarritoDTO>>> obtenerItemsCarritoPorCarrito(@PathVariable long id, @PathVariable int pagina){
        List<ItemCarritoDTO> itemsCarrito = itemCarritoService.obtenerItemsCarritoPorCarrito(pagina, id);
        return ResponseEntity.ok(ApiResponse.success(itemsCarrito, "ItemsCarrito obtenidos correctamente"));
    }

    @GetMapping("/carrito/{id}/pagina/{pagina}/activo")
    public ResponseEntity<ApiResponse<List<ItemCarritoDTO>>> obtenerItemsCarritoPorCarritoActivo(@PathVariable long id, @PathVariable int pagina){
        List<ItemCarritoDTO> itemsCarrito = itemCarritoService.obtenerItemsCarritoPorCarritoSiEsActivo(pagina, id);
        return ResponseEntity.ok(ApiResponse.success(itemsCarrito, "ItemsCarrito obtenidos correctamente"));
    }

    @GetMapping("/carrito/{id}/pagina/{pagina}/no-activo")
    public ResponseEntity<ApiResponse<List<ItemCarritoDTO>>> obtenerItemsCarritoPorCarritoSiNoEsActivo(@PathVariable long id, @PathVariable int pagina){
        List<ItemCarritoDTO> itemsCarrito = itemCarritoService.obtenerItemsCarritoPorCarritoSiNoEsActivo(pagina, id);
        return ResponseEntity.ok(ApiResponse.success(itemsCarrito, "ItemsCarrito obtenidos correctamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ItemCarritoDTO>> obtenerItemCarritoPorId(@PathVariable long id){
        ItemCarritoDTO itemCarrito = itemCarritoService.obtenerItemCarritoPorId(id);
        return ResponseEntity.ok(ApiResponse.success(itemCarrito, "ItemCarrito obtenido correctamente"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ItemCarritoDTO>> crearItemCarrito(@RequestBody CreateItemCarritoDTO carritoDTO){
        ItemCarritoDTO itemCarrito = itemCarritoService.crearItemCarrito(carritoDTO);
        return new ResponseEntity<>(ApiResponse.success(itemCarrito, "ItemCarrito creado correctamente"), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> actualizarItemCarrito(@PathVariable long id, @RequestBody UpdateItemCarritoDTO itemCarritoDTO) {
        itemCarritoService.actualizarItemCarrito(id, itemCarritoDTO);
        return ResponseEntity.ok(ApiResponse.success(null, "ItemCarrito actualizado correctamente"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> borrarItemCarrito(@PathVariable long id) {
        itemCarritoService.borrarItemCarrito(id);
        return ResponseEntity.ok(ApiResponse.success(null, "ItemCarrito borrado correctamente"));
    }

    @GetMapping("/existe/{id}")
    public ResponseEntity<ApiResponse<Boolean>> existeItemCarrito(@PathVariable long id) {
        Boolean existe = itemCarritoService.existeItemCarrito(id);
        return ResponseEntity.ok(ApiResponse.success(existe, "Existe el itemCarrito correctamente"));
    }

}
