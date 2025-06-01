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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador del carrito")
    private long id;

    // Relación 1 a 1 con UsuarioModel (bidireccional)
    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnore
    @Schema(description = "Usuario asociado al carrito")
    private UsuarioModel usuario;

    // Fecha de creación del carrito
    @Column(name = "fecha_creacion", nullable = false)
    @Schema(description = "Fecha de creación del carrito")
    private LocalDateTime fechaCreacion;

    // Relación con los ítems del carrito
    @OneToMany(mappedBy = "carrito", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @Schema(description = "Lista de ítems del carrito")
    private List<ItemCarritoModel> items;

    // Constructor personalizado
    public CarritoModel(UsuarioModel usuario) {
        this.usuario = usuario;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Inicializar fecha de creación automáticamente si no se proporciona
    @PrePersist
    public void prePersist() {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
    }
}
