package com.vegastore.jitarger.service;

import java.util.List;

import com.vegastore.jitarger.dto.base.ProveedorDTO;
import com.vegastore.jitarger.dto.create.CreateProveedorDTO;
import com.vegastore.jitarger.dto.update.UpdateProveedorDTO;

public interface ProveedorService {

    List<ProveedorDTO> obtenerProveedores();
    ProveedorDTO obtenerProveedorPorId(long id);
    ProveedorDTO obtenerProveedorPorRuc(String ruc);
    List<ProveedorDTO> buscarProveedoresPorNombre(String nombre);
    long crearProveedor(CreateProveedorDTO proveedorDTO);
    void actualizarProveedor(long id, UpdateProveedorDTO proveedorDTO);
    void borrarProveedor(long id);
    boolean existeProveedor(long id);
    boolean existeProveedorPorRuc(String ruc);
    
}