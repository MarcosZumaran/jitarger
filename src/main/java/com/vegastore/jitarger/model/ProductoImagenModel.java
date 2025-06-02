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
@Table(name = "producto_imagen")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductoImagenModel {
    
    // Atributos de la tabla producto_imagen
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema
    private long id;

    // Foreign key
    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private ProductoModel idProducto;

    @Column(name = "url", nullable = false, length = 255)
    private String url;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    // Constructor personalizado para la creación de objetos de la tabla producto_imagen
    public ProductoImagenModel(ProductoModel producto, String url, String tipo) {
        this.idProducto = producto;
        this.url = url;
        this.tipo = tipo;
    }
}
