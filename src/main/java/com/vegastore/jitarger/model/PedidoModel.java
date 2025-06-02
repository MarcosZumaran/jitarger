package com.vegastore.jitarger.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
    private UsuarioModel usuario;

    @Column(name = "fecha_registro")
    @Schema(description = "Fecha de creación del pedido")
    private LocalDateTime fechaRegistro;

    @Column(name = "total", precision = 10, scale = 2)
    @Schema(description = "Total del pedido")
    private BigDecimal total;

    @Column(name = "subtotal", precision = 10, scale = 2)
    @Schema(description = "Subtotal del pedido")
    private BigDecimal subtotal;

    @Column(name = "impuestos", precision = 10, scale = 2, nullable = true)
    @Schema(description = "impuestos del pedido")
    private BigDecimal impuestos;

    @Column(name = "metodo_pago", length = 50, nullable = false)
    @Schema(description = "Metodo de pago del pedido")
    private String metodoPago;

    @Column(name = "direccion_entrega", length = 255, nullable = false)
    @Schema(description = "Direccion de entrega del pedido")
    private String direccionEntrega;

    @Column(name = "fecha_entrega")
    @Schema(description = "Fecha de entrega del pedido")
    private LocalDateTime fechaEntrega;

    @Column(name = "estado", length = 50, nullable = false)
    @Schema(description = "Estado del pedido, puede ser 'PENDIENTE', 'ENVIADO', 'ENTREGADO', 'CANCELADO'")
    private String estado;

    @Column(name = "fecha_actualizacion")
    @Schema(description = "Fecha de actualización del pedido")
    private LocalDateTime fechaActualizacion;

    // Listas de relaciones
    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @Schema(description = "Lista de detalles del pedido")
    private List<DetallePedidoModel> detalles;

    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @Schema(description = "Lista de historiales del pedido")
    private List<HistorialPedidoModel> historiales;

    // Constructor personalizado para la creación de objetos de la tabla pedido

    public PedidoModel(UsuarioModel usuario, BigDecimal total, BigDecimal subtotal, BigDecimal impuestos, String metodoPago, String direccionEntrega) {
        this.usuario = usuario;
        this.total = total;
        this.subtotal = subtotal;
        this.impuestos = impuestos;
        this.metodoPago = metodoPago;
        this.direccionEntrega = direccionEntrega;
        this.fechaRegistro = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
        this.estado = "PENDIENTE"; // Por defecto, el estado del pedido es PENDIENTE al crearlo
    }

    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
        this.estado = "PENDIENTE"; // Por defecto, el estado del pedido es PENDIENTE al crearlo
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }

}
