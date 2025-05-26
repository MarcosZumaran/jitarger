package com.vegastore.jitarger.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto_presentacion")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductoPresentacionModel {

    // Atributos de la tabla producto_presentacion

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id de la presentación del producto")
    private long id;

    // Foreign key
    @ManyToOne
    @JoinColumn(name = "id_producto")
    @Schema(description = "Id del producto")
    private ProductoModel idProducto;

    @Column(name = "unidad_medida", length = 20, nullable = false)
    @Schema(description = "Unidad de medida de la presentación del producto")
    private String unidadMedida;

    @Column(name = "unidad_abreviatura", length = 6, nullable = false)
    @Schema(description = "Abreviatura de la unidad de medida de la presentación del producto")
    private String unidadAbreviatura;

    @Column(name = "equivalencia", nullable = false)
    @Schema(description = "Equivalencia de la presentación del producto en referencia a la unidad de medida base del lote")
    private int equivalencia;

    // Constructor personalizado para la creación de objetos de la tabla producto_presentacion
    public ProductoPresentacionModel(
        ProductoModel idProducto, 
        String unidadMedida, 
        String unidadAbreviatura, 
        int equivalencia
        ) {
        this.idProducto = idProducto;
        this.unidadMedida = unidadMedida;
        this.unidadAbreviatura = unidadAbreviatura;
        this.equivalencia = equivalencia;
    }
    
}
