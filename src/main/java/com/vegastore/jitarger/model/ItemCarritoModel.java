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
    private CarritoModel idCarrito;

    // Foreign key
    @ManyToOne
    @JoinColumn(name = "id_producto_presentacion")
    @Schema(description = "Identificador del producto")
    private ProductoPresentacionModel idProductoPresentacion;

    @Column(name = "cantidad", precision = 10, scale = 3)
    @Schema(description = "Cantidad del item del carrito")
    private BigDecimal cantidad;

    @Column(name = "fecha_agregado", nullable = false)
    @Schema(description = "Fecha de agregado del item al carrito")
    private LocalDateTime fechaAgregado;

    @Column(name = "precio_actual", precision = 10, scale = 2)
    @Schema(description = "Precio actual del item del carrito")
    private BigDecimal precioActual;

    @Column(name = "nombre_producto", length = 50, nullable = false)
    @Schema(description = "Nombre del producto")
    private String nombreProducto;

    @Column(name = "unidad_medida_presentacion", length = 20, nullable = false)
    @Schema(description = "Unidad de medida del producto")
    private String unidadmedidaPresentacion;

    @Column(name = "estado")
    @Schema(description = "Estado del item del carrito")
    private boolean activo;

    // Constructor personalizado para la creación de objetos de la tabla item_carrito

    public ItemCarritoModel( 
        CarritoModel idCarrito, 
        ProductoPresentacionModel idProductoPresentacion,
        BigDecimal cantidad, 
        LocalDateTime fechaAgregado, 
        BigDecimal precioActual, 
        String nombreProducto,
        String unidadmedidaPresentacion,
        boolean activo
        ) {  
        this.idCarrito = idCarrito;
        this.idProductoPresentacion = idProductoPresentacion;
        this.cantidad = cantidad;
        this.fechaAgregado = fechaAgregado;
        this.precioActual = precioActual;
        this.nombreProducto = nombreProducto;
        this.unidadmedidaPresentacion = unidadmedidaPresentacion;
        this.activo = activo;
    }
    
}
