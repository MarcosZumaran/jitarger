package com.vegastore.jitarger.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreatePedidoDTO {

    @NotNull(message = "El cliente no puede ser nulo")
    @Schema(description = "Identificador del cliente", example = "1")
    private long idUsuario;

    @Positive(message = "El fecha de creación del pedido no puede ser negativa")
    @Schema(description = "Fecha de creación del pedido", example = "2023-05-01T00:00:00")
    private String fechaRegistro;

    @Positive(message = "El estado del pedido no puede ser negativo")
    @Schema(description = "Estado del pedido", example = "1")
    private String estado;

    @Positive(message = "El total del pedido no puede ser negativo")
    @Schema(description = "Total del pedido", example = "3.99")    
    private BigDecimal total;

    @Positive(message = "El subtotal del pedido no puede ser negativo")
    @Schema(description = "Subtotal del pedido", example = "3.99")    
    private BigDecimal subtotal;

    @Positive(message = "El descuento del pedido no puede ser negativo")
    @Schema(description = "Descuento del pedido", example = "3.99")    
    private BigDecimal descuento;

    @Positive(message = "El impuestos del pedido no puede ser negativo")
    @Schema(description = "Impuestos del pedido", example = "3.99")    
    private BigDecimal impuestos;

    @Positive(message = "El metodo de pago del pedido no puede ser negativo")
    @Schema(description = "Metodo de pago del pedido", example = "1")    
    private String metodoPago;

    @Positive(message = "El direccion de entrega del pedido no puede ser negativo")
    @Schema(description = "Direccion de entrega del pedido", example = "1")    
    private String direccionEntrega;

    @Positive(message = "La fecha de confirmacion del pedido no puede ser negativa")
    @Schema(description = "Fecha de confirmacion del pedido", example = "2023-05-01T00:00:00")    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaConfirmacion;

    @Positive(message = "La fecha de entrega del pedido no puede ser negativa")
    @Schema(description = "Fecha de entrega del pedido", example = "2023-05-01T00:00:00")   
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
    private LocalDateTime fechaEntrega;

    @Positive(message = "La cancelado del pedido no puede ser negativa")
    @Schema(description = "Cancelado del pedido", example = "1")    
    private boolean cancelado;

    @Positive(message = "La fecha de cancelacion del pedido no puede ser negativa")
    @Schema(description = "Fecha de cancelacion del pedido", example = "2023-05-01T00:00:00")   
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCancelacion;
    
}
