package com.vegastore.jitarger.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;;

@Entity
@Table(name = "proveedor")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProveedorModel {

    // Atributos de la tabla proveedor

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador del proveedor")
    private long id;

    @Column(name = "nombre", length = 50, nullable = false)
    @Schema(description = "Nombre del proveedor")
    private String nombreEmpresa;

    @Column(name = "ruc", length = 11, nullable = false)
    @Schema(description = "RUC del proveedor")
    private String ruc;

    @Column(name = "direccion", length = 50, nullable = false)
    @Schema(description = "Dirección del proveedor")
    private String direccion;

    @Column(name = "telefono", length = 50, nullable = false)
    @Pattern(regexp = "^[0-9]{9,20}$", message = "El teléfono debe tener entre 9 y 20 dígitos")
    @Schema(description = "Telefono del proveedor")
    private String telefono;

    @Column(name = "correo", length = 100, nullable = false)
    @Schema(description = "Correo electrónico del proveedor")
    @Email(message = "El correo no es válido")
    private String correo;

    @Column(name = "fecha_registro", nullable = false)
    @Schema(description = "Fecha de creación del proveedor")
    private LocalDateTime fechaRegistro;

    @Column(name = "fecha_actualizacion")
    @Schema(description = "Fecha de actualización del proveedor")
    private LocalDateTime fechaActualizacion;

    @Column(name = "activo", nullable = false)
    @Schema(description = "Estado del proveedor, true si está activo, false si está inactivo")
    private boolean activo;

    // --- Listas de relaciones ---

    @OneToMany(mappedBy = "proveedor", fetch = FetchType.LAZY)
    @Schema(description = "Lista de lotes del proveedor")
    private List<LoteModel> lotes;

    // Constructor personalizado para la creación de objetos de la tabla proveedor

    public ProveedorModel(String nombreEmpresa, String ruc, String direccion, String telefono, String correo) {
        this.nombreEmpresa = nombreEmpresa;
        this.ruc = ruc;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaRegistro = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
        this.activo = true; // Por defecto, el proveedor está activo al crearlo
    }

    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
        this.activo = true; // Por defecto, el proveedor está activo al crearlo
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }

}
