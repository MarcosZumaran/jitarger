package com.vegastore.jitarger.model;

import java.math.BigDecimal;

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
@Table(name = "detalle_pedido")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DetallePedidoModel {

    // Atributos de la tabla detalle_pedido

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador del detalle del pedido")
    private long id;

    // Foreign key
    @ManyToOne
    @JoinColumn(name = "id_pedido")
    @Schema(description = "Identificador del pedido")
    private PedidoModel pedido;

    // Foreign key
    @ManyToOne
    @JoinColumn(name = "id_producto_presentacion")
    @Schema(description = "Identificador del producto")
    private ProductoPresentacionModel productoPresentacion;

    // Foreign key
    @ManyToOne
    @JoinColumn(name = "id_lote")
    @Schema(description = "Identificador del lote")
    private LoteModel lote;

    @Column(name = "cantidad", precision = 10, scale = 3)
    @Schema(description = "Cantidad del detalle del pedido")
    private BigDecimal cantidad;

    @Column(name = "precio_unitario", precision = 10, scale = 2)
    @Schema(description = "Precio unitario del detalle del pedido")
    private BigDecimal precioUnitario;

    @Column(name = "subtotal", precision = 10, scale = 2)
    @Schema(description = "Precio subtotal del detalle del pedido")
    private BigDecimal subTotal;

    @Column(name = "nombre_producto", length = 50)
    @Schema(description = "Nombre del producto")
    private String nombreProducto;

    @Column(name = "unidad_medida_presentacion", length = 20)
    @Schema(description = "Unidad de medida del producto")
    private String unidadmedidaPresentacion;

    // Constructor personalizado para la creación de objetos de la tabla detalle_pedido
    public DetallePedidoModel(PedidoModel pedido, ProductoPresentacionModel productoPresentacion, LoteModel lote,
                                BigDecimal cantidad, BigDecimal precioUnitario, BigDecimal subTotal,
                                String nombreProducto, String unidadmedidaPresentacion) {
        this.pedido = pedido;
        this.productoPresentacion = productoPresentacion;
        this.lote = lote;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subTotal = subTotal;
        this.nombreProducto = nombreProducto;
        this.unidadmedidaPresentacion = unidadmedidaPresentacion;
    }
    
}
