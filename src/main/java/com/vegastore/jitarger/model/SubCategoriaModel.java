package com.vegastore.jitarger.model;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subcategoria")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SubCategoriaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private CategoriaModel categoria;

    @Column(name = "nombre", length = 50, nullable = false)
    @Schema(description = "Nombre de la subcategoria")
    private String nombre;

    @Column(name = "descripcion", length = 160, nullable = false)
    @Schema(description = "Descripción de la subcategoria")
    private String descripcion;

    // Listas de relaciones

    @OneToMany(mappedBy = "idSubCategoria", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "Lista de productos de la subcategoria")
    private List<ProductoModel> productos;

    // Constructor personalizado para la creación de objetos de la tabla subcategoria

    public SubCategoriaModel(
        CategoriaModel categoria,
        String nombre, 
        String descripcion
        ) {
        this.categoria = categoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}
