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
import jakarta.persistence.OneToOne;
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

    @JsonIgnore
    @Column(name = "clave", length = 50, nullable = false)
    @Schema(description = "Clave del usuario")
    private String clave;

    @Column(name = "rol", length = 50, nullable = false)
    @Schema(description = "Rol del usuario")
    private String rol;

    @Column(name = "fecha_registro", nullable = false)
    @Schema(description = "Fecha de creación del usuario")
    private LocalDateTime fechaRegistro;

    @Column(name = "fecha_actualizacion")
    @Schema(description = "Fecha de última actualización del usuario")
    private LocalDateTime fechaActualizacion;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "Lista de pedidos del usuario")
    private List<PedidoModel> pedidos;

    @JsonIgnore
    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CarritoModel carrito;

    // Constructor personalizado
    public UsuarioModel(
        String nombre, 
        String apellido, 
        String correo, 
        String telefono,
        String clave, 
        String rol, 
        LocalDateTime fechaRegistro
    ) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.clave = clave;
        this.rol = rol;
        this.fechaRegistro = fechaRegistro;
    }

    // PrePersist para asignar la fecha de creación
    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    // PreUpdate para asignar la fecha de actualización
    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }
}
