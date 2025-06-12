package com.vegastore.jitarger.service;

import java.util.List;

import com.vegastore.jitarger.dto.base.UsuarioDTO;
import com.vegastore.jitarger.dto.create.CreateUsuarioDTO;
import com.vegastore.jitarger.dto.update.UpdateUsuarioDTO;

public interface UsuarioService {

    List<UsuarioDTO> obtenerUsuarios(int pagina);
    List<UsuarioDTO> obtenerUsuariosPorNombre(int pagina, String nombre);
    List<UsuarioDTO> obtenerUsuariosPorRol(int pagina, String rol);
    List<UsuarioDTO> obtenerTodosLosUsuarios();
    List<UsuarioDTO> obtenerTodosLosUsuarioPorNombre(String nombre);
    List<UsuarioDTO> obtenerTodosLosUsuariosPorRol(String rol);
    UsuarioDTO obtenerUsuarioPorId(long id);
    UsuarioDTO obtenerusuarioPorCredenciales(String correo, String clave);
    UsuarioDTO crearUsuario(CreateUsuarioDTO usuarioDTO);
    void actualizarUsuario(long id, UpdateUsuarioDTO usuarioDTO);
    void cambiarClave(long id, String nuevaClave);
    void borrarUsuario(long id);
    boolean existeUsuario(long id);
    
}