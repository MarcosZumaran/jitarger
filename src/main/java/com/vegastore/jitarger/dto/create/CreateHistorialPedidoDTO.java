package com.vegastore.jitarger.dto.create;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateHistorialPedidoDTO {
    
    @Positive(message = "El id del pedido no puede ser negativo")
    @NotNull(message = "El id del pedido no puede ser nulo")
    @Schema(description = "Identificador del pedido", example = "1")
    private long idPedido;

    @NotBlank(message = "El estado anterior del pedido no puede estar en blanco")
    @Length(min = 3, max = 50, message = "El estado antrior del pedido debe tener entre 3 y 50 caracteres")
    @Schema(description = "Estado anterior del pedido")
    private String estadoAnterior;

    @NotBlank(message = "El estado nuevo del pedido no puede estar en blanco")
    @Length(min = 3, max = 50, message = "El estado nuevo del pedido debe tener entre 3 y 50 caracteres")
    @Schema(description = "Estado nuevo del pedido")
    private String estadoNuevo;

    @NotNull(message = "La fecha de cambio del estado del pedido no puede ser nula")
    @Schema(description = "Fecha de cambio del estado del pedido")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCambio;

    @NotBlank(message = "El comentario del cambio de estado no puede estar en blanco")
    @Length(min = 3, max = 255, message = "El comentario del cambio de estado debe tener entre 3 y 255 caracteres")
    @Schema(description = "Comentario del cambio de estado")
    private String comentario;

}
