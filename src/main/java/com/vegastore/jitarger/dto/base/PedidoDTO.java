package com.vegastore.jitarger.dto.base;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
public class PedidoDTO {

    
    @Schema(description = "Identificador del pedido", example = "1")
    private long id;

    
    @Schema(description = "Identificador del cliente", example = "1")
    private long idUsuario;

    
    @Schema(description = "Fecha de creación del pedido", example = "2023-05-01T00:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaRegistro;

    
    @Schema(description = "Estado del pedido", example = "PENDIENTE")
    private String estado;

    
    @Schema(description = "Total del pedido", example = "3.99")    
    private BigDecimal total;

    
    @Schema(description = "Subtotal del pedido", example = "3.99")    
    private BigDecimal subtotal;

    
    @Schema(description = "Descuento del pedido", example = "3.99")    
    private BigDecimal descuento;

    
    @Schema(description = "Impuestos del pedido", example = "3.99")    
    private BigDecimal impuestos;

    @Schema(description = "Metodo de pago del pedido", example = "1")    
    private String metodoPago;

    @Schema(description = "Direccion de entrega del pedido", example = "1")    
    private String direccionEntrega;

    @Schema(description = "Fecha de confirmacion del pedido", example = "2023-05-01T00:00:00")    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaConfirmacion;

    @Schema(description = "Fecha de entrega del pedido", example = "2023-05-01T00:00:00")   
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
    private LocalDateTime fechaEntrega;

    @Schema(description = "Cancelado del pedido", example = "1")    
    private boolean cancelado;

    @Schema(description = "Fecha de cancelacion del pedido", example = "2023-05-01T00:00:00")   
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCancelacion;
    
}
