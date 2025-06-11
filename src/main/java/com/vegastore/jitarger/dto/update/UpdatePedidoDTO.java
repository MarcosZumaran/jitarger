package com.vegastore.jitarger.dto.update;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdatePedidoDTO {

    @Schema(description = "Estado del pedido", example = "PENDIENTE")
    private String estado;

    @Schema(description = "Fecha de confirmación del pedido", example = "2023-05-01T00:00:00")    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaConfirmacion;

    @Schema(description = "Fecha de entrega del pedido", example = "2023-05-01T00:00:00")   
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
    private LocalDateTime fechaEntrega;

    @Schema(description = "¿El pedido fue cancelado?", example = "false")    
    private Boolean cancelado;

    @Schema(description = "Fecha de cancelación del pedido", example = "2023-05-01T00:00:00")   
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCancelacion;

    @Schema(description = "Método de pago", example = "Tarjeta de crédito")
    private String metodoPago;

    @Schema(description = "Dirección de entrega", example = "Av. Siempre Viva 123")
    private String direccionEntrega;

    @Schema(description = "Subtotal", example = "100.00")
    private BigDecimal subtotal;

    @Schema(description = "Descuento aplicado", example = "10.00")
    private BigDecimal descuento;

    @Schema(description = "Impuestos", example = "15.00")
    private BigDecimal impuestos;

    @Schema(description = "Total final del pedido", example = "105.00")
    private BigDecimal total;
    
}
