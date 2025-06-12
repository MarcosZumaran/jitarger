package com.vegastore.jitarger.service;

import java.util.List;

import com.vegastore.jitarger.dto.base.ProveedorDTO;
import com.vegastore.jitarger.dto.create.CreateProveedorDTO;
import com.vegastore.jitarger.dto.update.UpdateProveedorDTO;

public interface ProveedorService {

    List<ProveedorDTO> obtenerProveedores(int pagina);
    List<ProveedorDTO> obtenerTodosLosProveedores();
    List<ProveedorDTO> obtenerProveedoresPorNombre(String nombre);
    List<ProveedorDTO> obtenerProveedoresPorActivo(boolean activo);
    ProveedorDTO obtenerProveedorPorId(long id);
    ProveedorDTO obtenerProveedorPorRuc(String ruc);
    ProveedorDTO crearProveedor(CreateProveedorDTO proveedorDTO);
    void actualizarProveedor(long id, UpdateProveedorDTO proveedorDTO);
    void borrarProveedor(long id);
    boolean existeProveedor(long id);
    
}