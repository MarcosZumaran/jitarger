package com.vegastore.jitarger.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioModel {

    // Atributos de la tabla usuario

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador del usuario")
    private long id;

    @Column(name = "nombre", length = 50, nullable = false)
    @Schema(description = "Nombre del usuario")
    private String nombre;

    @Column(name = "apellido", length = 50, nullable = false)
    @Schema(description = "Apellido del usuario")
    private String apellido;

    @Column(name = "email", length = 100, nullable = false)
    @Schema(description = "Correo electrónico del usuario")
    @Email(message = "El correo no es válido")
    private String correo;

    @Column(name = "telefono", length = 50, nullable = false)
    @Pattern(regexp = "^[0-9]{9,20}$", message = "El teléfono debe tener entre 9 y 20 dígitos")
    @Schema(description = "Telefono del usuario")
    private String telefono;

    @Column(name = "clave", length = 50, nullable = false)
    @Schema(description = "Clave del usuario")
    private String clave;

    @Column(name = "rol", length = 50, nullable = false)
    @Schema(description = "Rol del usuario,  o cliente")
    private String rol;

    @Column(name = "fecha_registro", nullable = false)
    @Schema(description = "Fecha de creación del usuario")
    private LocalDateTime fechaRegistro;

    @Column(name = "fecha_actualizacion", nullable = false)
    @Schema(description = "Fecha de actualización del usuario")
    private LocalDateTime fechaActualizacion;

    @Column(name = "activo", nullable = false)
    @Schema(description = "Estado del usuario, activo o inactivo. Activo significa que el usuario puede realizar acciones en la aplicación")
    private boolean activo;

    // --- Listas de relaciones ---

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "Lista de pedidos del usuario")
    @JsonIgnore
    private List<PedidoModel> pedidos;

    // Relacion 1 a N con el carrito
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "Lista de carritos del usuario")
    @JsonIgnore
    private List<CarritoModel> carrito;

    // Constructor personalizado para la creación de objetos de la tabla usuario
    public UsuarioModel(String nombre, String apellido, String correo, String telefono, String clave, String rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.clave = clave;
        this.rol = rol;
        this.fechaRegistro = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
        this.activo = true; // Por defecto el usuario está activo al crearse
    }

    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
        this.activo = true; // Por defecto el usuario está activo al crearse
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }

}
