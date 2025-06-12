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

import com.vegastore.jitarger.dto.base.UsuarioDTO;
import com.vegastore.jitarger.dto.create.CreateUsuarioDTO;
import com.vegastore.jitarger.dto.update.UpdateUsuarioDTO;
import com.vegastore.jitarger.responce.ApiResponse;
import com.vegastore.jitarger.service.UsuarioService;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/usuario")
public class UsuarioRestController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioRestController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> obtenerTodosLosUsuarios(){
        List<UsuarioDTO> usuarios = usuarioService.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(ApiResponse.success(usuarios, "Usuarios obtenidos correctamente"));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> obtenerTodosLosUsuariosPorNombre(@PathVariable String nombre){
        List<UsuarioDTO> usuarios = usuarioService.obtenerTodosLosUsuarioPorNombre(nombre);
        return ResponseEntity.ok(ApiResponse.success(usuarios, "Usuarios obtenidos correctamente"));
    }

    @GetMapping("/rol/{rol}")
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> obtenerTodosLosUsuariosPorRol(@PathVariable String rol){
        List<UsuarioDTO> usuarios = usuarioService.obtenerTodosLosUsuariosPorRol(rol);
        return ResponseEntity.ok(ApiResponse.success(usuarios, "Usuarios obtenidos correctamente"));
    }

    @GetMapping("/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> obtenerUsuarios(@PathVariable int pagina){
        List<UsuarioDTO> usuarios = usuarioService.obtenerUsuarios(pagina);
        return ResponseEntity.ok(ApiResponse.success(usuarios, "Usuarios obtenidos correctamente"));
    }

    @GetMapping("/nombre/{nombre}/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> obtenerUsuariosPorNombre(@PathVariable String nombre, @PathVariable int pagina){
        List<UsuarioDTO> usuarios = usuarioService.obtenerUsuariosPorNombre(pagina, nombre);
        return ResponseEntity.ok(ApiResponse.success(usuarios, "Usuarios obtenidos correctamente"));
    }

    @GetMapping("/rol/{rol}/pagina/{pagina}")
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> obtenerUsuariosPorRol(@PathVariable String rol, @PathVariable int pagina){
        List<UsuarioDTO> usuarios = usuarioService.obtenerUsuariosPorRol(pagina, rol);
        return ResponseEntity.ok(ApiResponse.success(usuarios, "Usuarios obtenidos correctamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioDTO>> obtenerUsuarioPorId(@PathVariable long id){
        UsuarioDTO usuario = usuarioService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(ApiResponse.success(usuario, "Usuario obtenido correctamente"));
    }

    @GetMapping("/credenciales/{correo}/{clave}")
    public ResponseEntity<ApiResponse<UsuarioDTO>> obtenerusuarioPorCredenciales(@PathVariable String correo, @PathVariable String clave){
        UsuarioDTO usuario = usuarioService.obtenerusuarioPorCredenciales(correo, clave);
        return ResponseEntity.ok(ApiResponse.success(usuario, "Usuario logueado correctamente"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UsuarioDTO>> crearUsuario(@RequestBody CreateUsuarioDTO usuario){
        UsuarioDTO usuarioCreado = usuarioService.crearUsuario(usuario);
        return new ResponseEntity<>(ApiResponse.success(usuarioCreado, "Usuario creado correctamente"), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> actualizarUsuario(@PathVariable long id, @RequestBody UpdateUsuarioDTO usuario){
        usuarioService.actualizarUsuario(id, usuario);
        return ResponseEntity.ok(ApiResponse.success(null, "Usuario actualizado correctamente"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> borrarUsuario(@PathVariable long id) {
        usuarioService.borrarUsuario(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Usuario borrado correctamente"));
    }

    @GetMapping("/existe/{id}")
    public ResponseEntity<ApiResponse<Boolean>> existeUsuario(@PathVariable long id) {
        boolean existe = usuarioService.existeUsuario(id);
        return ResponseEntity.ok(ApiResponse.success(existe, "Usuario obtenido correctamente"));
    }
    
}
