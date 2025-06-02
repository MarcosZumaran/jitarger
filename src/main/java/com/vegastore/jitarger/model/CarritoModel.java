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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "carrito")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CarritoModel {

    // Atributos de la tabla carrito

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador del carrito")
    private long id;

    // Foreign key relacion N a 1
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @Schema(description = "Id del usuario")
    private UsuarioModel usuario;

    @Column(name = "fecha_creacion", nullable = false)
    @Schema(description = "Fecha de creación del carrito")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_cambio_estado")
    @Schema(description = "Fecha del último cambio de estado del carrito")
    private LocalDateTime fechaCambioEstado;

    @Column(name = "estado", length = 20, nullable = false)
    @Schema(description = "Estado del carrito, puede ser 'ACTIVO', 'PROCESADO' o 'CANCELADO'")
    private String estado;

    // Listas de relaciones
    @OneToMany(mappedBy = "carrito", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "Lista de items del carrito")
    @JsonIgnore
    private List<ItemCarritoModel> items;

    // Constructor personalizado para la creación de objetos de la tabla carrito

    public CarritoModel(UsuarioModel usaurio, String estado) {
        this.usuario = usaurio;
        this.estado = estado;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaCambioEstado = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaCambioEstado = LocalDateTime.now();
        this.estado = "ACTIVO";
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaCambioEstado = LocalDateTime.now();
    }

}
