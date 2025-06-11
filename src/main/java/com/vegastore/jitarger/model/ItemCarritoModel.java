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
@Table(name = "item_carrito")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ItemCarritoModel {

    // Atributos de la tabla item_carrito

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador del item del carrito")
    private long id;

    // Foreign key
    @ManyToOne
    @JoinColumn(name = "id_carrito")
    @Schema(description = "Identificador del carrito")
    private CarritoModel carrito;

    // Foreign key
    @ManyToOne
    @JoinColumn(name = "id_producto_presentacion")
    @Schema(description = "Identificador del producto")
    private ProductoPresentacionModel productoPresentacion;

    // Foreign key
    @ManyToOne
    @JoinColumn(name = "id_producto_imagen")
    @Schema(description = "Identificador de la imagen del producto, para mostrarlo en el carrito")
    private ProductoImagenModel productoImagen;

    @Column(name = "cantidad", precision = 10, scale = 3)
    @Schema(description = "Cantidad del item del carrito")
    private BigDecimal cantidad;

    @Column(name = "fecha_agregado")
    @Schema(description = "Fecha de agregado del item al carrito")
    private LocalDateTime fechaAgregado;

    @Column(name = "precio_actual", precision = 10, scale = 2)
    @Schema(description = "Precio actual del item del carrito")
    private BigDecimal precioActual;

    @Column(name = "nombre_producto", length = 50)
    @Schema(description = "Nombre del producto")
    private String nombreProducto;

    @Column(name = "unidad_medida_presentacion", length = 20)
    @Schema(description = "Unidad de medida del producto")
    private String unidadmedidaPresentacion;

    @Column(name = "activo")
    @Schema(description = "Estado del item del carrito")
    private boolean activo;

    // Constructor personalizado para la creación de objetos de la tabla item_carrito

    public ItemCarritoModel(CarritoModel carrito, ProductoPresentacionModel productoPresentacion, ProductoImagenModel productoImagen, BigDecimal cantidad, BigDecimal precioActual, String nombreProducto, String unidadmedidaPresentacion) {
        this.carrito = carrito;
        this.productoPresentacion = productoPresentacion;
        this.productoImagen = productoImagen;
        this.cantidad = cantidad;
        this.fechaAgregado = LocalDateTime.now();
        this.precioActual = precioActual;
        this.nombreProducto = nombreProducto;
        this.unidadmedidaPresentacion = unidadmedidaPresentacion;
        this.activo = true; // Por defecto el item está activo al ser agregado
    }

}
