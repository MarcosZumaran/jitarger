package com.vegastore.jitarger.model;

import java.math.BigDecimal;
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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pedido")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PedidoModel {
    // Atributos de la tabla pedido

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador del pedido")
    private long id;

    // Foreign key
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @Schema(description = "Identificador del cliente")
    private UsuarioModel idUsuario;

    @Column(name = "fecha_registro")
    @Schema(description = "Fecha de creación del pedido")
    private LocalDateTime fechaRegistro;

    @Column(name = "estado", length = 50, nullable = false)
    @Schema(description = "Estado del pedido")
    private String estado;

    @Column(name = "total", precision = 10, scale = 2)
    @Schema(description = "Total del pedido")
    private BigDecimal total;

    @Column(name = "subtotal", precision = 10, scale = 2)
    @Schema(description = "Subtotal del pedido")
    private BigDecimal subtotal;

    @Column(name = "descuento", precision = 10, scale = 2, nullable = true)
    @Schema(description = "Descuento del pedido")
    private BigDecimal descuento;

    @Column(name = "iva", precision = 10, scale = 2, nullable = true)
    @Schema(description = "impuestos del pedido")
    private BigDecimal impuestos;

    @Column(name = "metodo_pago", length = 50, nullable = false)
    @Schema(description = "Metodo de pago del pedido")
    private String metodoPago;

    @Column(name = "direccion_entrega", length = 255, nullable = false)
    @Schema(description = "Direccion de entrega del pedido")
    private String direccionEntrega;

    @Column(name = "fecha_confirmacion")
    @Schema(description = "Fecha de confirmacion del pedido")
    private LocalDateTime fechaConfirmacion;

    @Column(name = "fecha_entrega")
    @Schema(description = "Fecha de entrega del pedido")
    private LocalDateTime fechaEntrega;

    @Column(name = "cancelado", nullable = false)
    @Schema(description = "Cancelado del pedido")
    private boolean cancelado;

    @Column(name = "fecha_cancelacion")
    @Schema(description = "Fecha de cancelacion del pedido")
    private LocalDateTime fechaCancelacion;

    // Listas de relaciones
    @OneToMany(mappedBy = "idPedido", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "Lista de detalles del pedido")
    private List<DetallePedidoModel> detalles;

    @OneToMany(mappedBy = "idPedido", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "Lista de historiales del pedido")
    private List<HistorialPedidoModel> historiales;

    // Constructor personalizado para la creación de objetos de la tabla pedido

    public PedidoModel(
        UsuarioModel idUsuario, 
        LocalDateTime fechaRegistro, 
        String estado, 
        BigDecimal total,
        BigDecimal subtotal, 
        BigDecimal descuento, 
        BigDecimal impuestos, 
        String metodoPago, 
        String direccionEntrega,
        LocalDateTime fechaConfirmacion, 
        LocalDateTime fechaEntrega, 
        boolean cancelado,
        LocalDateTime fechaCancelacion
        ) {
        this.idUsuario = idUsuario;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.total = total;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.impuestos = impuestos;
        this.metodoPago = metodoPago;
        this.direccionEntrega = direccionEntrega;
        this.fechaConfirmacion = fechaConfirmacion;
        this.fechaEntrega = fechaEntrega;
        this.cancelado = cancelado;
        this.fechaCancelacion = fechaCancelacion;
    }

}
