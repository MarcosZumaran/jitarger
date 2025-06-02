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
import jakarta.persistence.OneToMany;
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

    // Atributos de la tabla categoria

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador de la categoria")
    private long id;

    @Column(name = "nombre", length = 50, nullable = false)
    @Schema(description = "Nombre de la categoria")
    private String nombre;

    @Column(name = "descripcion", length = 160, nullable = false)
    @Schema(description = "Descripción de la categoria")
    private String descripcion;

    @Column(name = "fecha_registro", nullable = false)
    @Schema(description = "Fecha de creación de la categoria")
    private LocalDateTime fechaRegistro;


    // Listas de relaciones

    @OneToMany(mappedBy = "idCategoria", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "Lista de productos de la categoria")
    private List<ProductoModel> productos;

    @OneToMany(mappedBy = "idCategoria", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "Lista de subcategorias de la categoria")
    private List<SubCategoriaModel> subcategorias;

    // Constructor personalizado para la creación de objetos de la tabla categoria

    public CategoriaModel(
        String nombre, 
        String descripcion, 
        LocalDateTime fechaRegistro
        ) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaRegistro = fechaRegistro;
    }
    
}
