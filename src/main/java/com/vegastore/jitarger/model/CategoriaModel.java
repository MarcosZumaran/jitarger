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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categoria")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoriaModel {

    // Atributos de la tabla categoria

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador de la categoria")
    private long id;

    @Column(name = "nombre", length = 50)
    @Schema(description = "Nombre de la categoria")
    private String nombre;

    @Column(name = "descripcion", length = 160)
    @Schema(description = "Descripción de la categoria")
    private String descripcion;

    @Column(name = "fecha_registro")
    @Schema(description = "Fecha de creación de la categoria")
    private LocalDateTime fechaRegistro;

    @Column(name = "fecha_actualizacion")
    @Schema(description = "Fecha de actualización de la categoria")
    private LocalDateTime fechaActualizacion;

    @Column(name = "activa")
    @Schema(description = "Estado de la categoria, true si está activa, false si está inactiva")
    private boolean activa;


    // Listas de relaciones

    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "Lista de productos de la categoria")
    @JsonIgnore
    private List<ProductoModel> productos;

    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "Lista de subcategorias de la categoria")
    @JsonIgnore
    private List<SubCategoriaModel> subcategorias;

    // Constructor personalizado para la creación de objetos de la tabla categoria

    public CategoriaModel(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaRegistro = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
        this.activa = true; // Por defecto, la categoria está activa al ser creada
    }
    
}
