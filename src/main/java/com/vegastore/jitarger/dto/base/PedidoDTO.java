package com.vegastore.jitarger.dto.base;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    @Positive(message = "El id del pedido debe ser un número positivo")
    @Schema(description = "Identificador único del pedido", example = "1")
    private long id;

    @Positive(message = "El id del usuario debe ser un número positivo")
    @Schema(description = "Identificador del usuario que realizó el pedido", example = "1")
    private long idUsuario;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Fecha de creación del pedido", example = "2025-06-10 14:30:00")
    private LocalDateTime fechaRegistro;

    @Schema(description = "Estado actual del pedido", example = "PENDIENTE")
    private String estado;

    @Schema(description = "Monto total del pedido", example = "59.99")
    private BigDecimal total;

    @Schema(description = "Subtotal antes de descuentos e impuestos", example = "50.00")
    private BigDecimal subtotal;

    @Schema(description = "Monto de descuento aplicado", example = "5.00")
    private BigDecimal descuento;

    @Schema(description = "Monto total de impuestos aplicados", example = "9.99")
    private BigDecimal impuestos;

    @Schema(description = "Método de pago utilizado", example = "TARJETA")
    private String metodoPago;

    @Schema(description = "Dirección de entrega del pedido", example = "Av. Las Palmas 123, Lima")
    private String direccionEntrega;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Fecha en la que se confirmó el pedido", example = "2025-06-10 15:00:00")
    private LocalDateTime fechaConfirmacion;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Fecha estimada o real de entrega", example = "2025-06-12 18:00:00")
    private LocalDateTime fechaEntrega;

    @Schema(description = "Indica si el pedido fue cancelado", example = "false")
    private boolean cancelado;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Fecha en la que se canceló el pedido, si aplica", example = "2025-06-11 10:00:00")
    private LocalDateTime fechaCancelacion;
}
