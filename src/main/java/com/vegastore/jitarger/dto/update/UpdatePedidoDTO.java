package com.vegastore.jitarger.dto.update;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdatePedidoDTO {

    @Positive(message = "El fecha de creación del pedido no puede ser negativa")
    @Schema(description = "Fecha de creación del pedido", example = "2023-05-01T00:00:00")
    private String fechaRegistro;

    @Positive(message = "El estado del pedido no puede ser negativo")
    @Schema(description = "Estado del pedido", example = "1")
    private String estado;

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
