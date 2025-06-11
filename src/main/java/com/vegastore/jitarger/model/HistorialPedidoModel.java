package com.vegastore.jitarger.model;

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
@Table(name = "historial_pedido")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HistorialPedidoModel {

    // Atributos de la tabla historial_pedido

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador del historial del pedido")
    private long id;

    // Foreign key
    @ManyToOne
    @JoinColumn(name = "id_pedido")
    @Schema(description = "Identificador del pedido")
    private PedidoModel pedido;

    @Column(name = "estado_anterior", length = 50)
    @Schema(description = "Estado del pedido")
    private String estadoAnterior;

    @Column(name = "estado_nuevo", length = 50)
    @Schema(description = "Estado nuevo del pedido")
    private String estadoNuevo;

    @Column(name = "fecha_cambio")
    @Schema(description = "Fecha de cambio del estado del pedido")
    private LocalDateTime fechaCambio;

    @Column(name = "comentario", length = 255)
    @Schema(description = "Comentario del cambio de estado")
    private String comentario;

    // Constructor personalizado para la creación de objetos de la tabla historial_pedido

    public HistorialPedidoModel(PedidoModel pedido, String estadoAnterior, String estadoNuevo, String comentario) {
        this.pedido = pedido;
        this.estadoAnterior = estadoAnterior;
        this.estadoNuevo = estadoNuevo;
        this.fechaCambio = LocalDateTime.now();
        this.comentario = comentario;
    }

}
