package com.vegastore.jitarger.model;

import java.time.LocalDateTime;
import java.util.List;

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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subcategoria")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SubCategoriaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private CategoriaModel categoria;

    @Column(name = "nombre", length = 50)
    @Schema(description = "Nombre de la subcategoria")
    private String nombre;

    @Column(name = "descripcion", length = 160)
    @Schema(description = "Descripción de la subcategoria")
    private String descripcion;

    @Column(name = "fecha_registro")
    @Schema(description = "Fecha de creación de la subcategoria")
    private LocalDateTime fechaRegistro;

    @Column(name = "fecha_actualizacion")
    @Schema(description = "Fecha de actualización de la subcategoria")
    private LocalDateTime fechaActualizacion;

    @Column(name = "activa")
    @Schema(description = "Estado de la subcategoria, true si está activa, false si está inactiva")
    private boolean activa;

    // --- Listas de relaciones ---

    @OneToMany(mappedBy = "subcategoria", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "Lista de productos de la subcategoria")
    private List<ProductoModel> productos;

    // Constructor personalizado para la creación de objetos de la tabla subcategoria
    public SubCategoriaModel(CategoriaModel categoria, String nombre, String descripcion) {
        this.categoria = categoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaRegistro = LocalDateTime.now();
        this.activa = true; // Por defecto, la subcategoria está activa al crearla
    }

}
