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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categoria")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoriaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador de la categoría")
    private long id;

    @Column(name = "nombre", length = 50, nullable = false)
    @Schema(description = "Nombre de la categoría")
    private String nombre;

    @Column(name = "descripcion", length = 160, nullable = false)
    @Schema(description = "Descripción de la categoría")
    private String descripcion;

    @Column(name = "fecha_registro", nullable = false)
    @Schema(description = "Fecha de registro de la categoría")
    private LocalDateTime fechaRegistro;

    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @Schema(description = "Lista de productos de la categoría")
    private List<ProductoModel> productos;

    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @Schema(description = "Lista de subcategorías de la categoría")
    private List<SubCategoriaModel> subcategorias;

    // Constructor personalizado
    public CategoriaModel(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaRegistro = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        if (fechaRegistro == null) {
            fechaRegistro = LocalDateTime.now();
        }
    }
}
