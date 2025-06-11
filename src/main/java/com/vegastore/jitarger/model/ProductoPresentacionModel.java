package com.vegastore.jitarger.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private ProductoModel producto;

    @Column(name = "unidad_medida", length = 20)
    @Schema(description = "Unidad de medida de la presentación del producto")
    private String unidadMedida;

    @Column(name = "unidad_abreviatura", length = 6)
    @Schema(description = "Abreviatura de la unidad de medida de la presentación del producto")
    private String unidadAbreviatura;

    @Column(name = "equivalencia", precision = 10, scale = 3)
    @Schema(description = "Equivalencia de la presentación del producto en referencia a la unidad de medida base del lote")
    private BigDecimal equivalencia;

    @Column(name = "disponible")
    @Schema(description = "Indica si la presentación del producto está disponible")
    private boolean disponible;

    @Column(name = "fecha_creacion")
    @Schema(description = "Fecha de creación de la presentación del producto")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    @Schema(description = "Fecha de actualización de la presentación del producto")
    private LocalDateTime fechaActualizacion;

    // Constructor personalizado para la creación de objetos de la tabla producto_presentacion

    public ProductoPresentacionModel(ProductoModel producto, String unidadMedida, String unidadAbreviatura, BigDecimal equivalencia, boolean disponible) {
        this.producto = producto;
        this.unidadMedida = unidadMedida;
        this.unidadAbreviatura = unidadAbreviatura;
        this.equivalencia = equivalencia;
        this.disponible = disponible;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }
        
}
