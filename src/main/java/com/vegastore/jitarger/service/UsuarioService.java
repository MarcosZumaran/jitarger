package com.vegastore.jitarger.service;

import java.util.List;

import com.vegastore.jitarger.dto.base.UsuarioDTO;
import com.vegastore.jitarger.dto.create.CreateUsuarioDTO;
import com.vegastore.jitarger.dto.update.UpdateUsuarioDTO;

public interface UsuarioService {

    List<UsuarioDTO> obtenerUsuarios();
    List<UsuarioDTO> obtenerUsuariosPorRol(String rol);
    UsuarioDTO obtenerUsuarioPorId(long id);
    UsuarioDTO obtenerUsuarioPorCorreo(String correo);
    UsuarioDTO obtenerUsuarioPorTelefono(String telefono);
    long crearUsuario(CreateUsuarioDTO usuarioDTO);
    void actualizarUsuario(long id, UpdateUsuarioDTO usuarioDTO);
    void cambiarClave(long id, String nuevaClave);
    void borrarUsuario(long id);
    boolean existeUsuario(long id);
    boolean existeUsuarioPorCorreo(String correo);
    boolean existeUsuarioPorTelefono(String telefono);
    boolean validarCredenciales(String correo, String clave);
    
}