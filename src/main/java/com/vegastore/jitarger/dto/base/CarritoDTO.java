package com.vegastore.jitarger.dto.base;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarritoDTO {

    @Positive(message = "El id del carrito debe ser un número positivo")
    @Schema(description = "Identificador único del carrito", example = "1")
    private long id;

    @NotNull(message = "El id del usuario no puede ser nulo")
    @Positive(message = "El id del usuario debe ser un número positivo")
    @Schema(description = "Identificador del usuario asociado al carrito", example = "1")
    private long idUsuario;

    @NotNull(message = "La fecha de creación no puede ser nula")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Fecha de creación del carrito", example = "2025-06-10 15:30:00")
    private LocalDateTime fechaCreacion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Fecha del último cambio de estado del carrito", example = "2025-06-11 10:15:00")
    private LocalDateTime fechaCambioEstado;

    @NotNull(message = "El estado del carrito no puede ser nulo")
    @Schema(description = "Estado actual del carrito", example = "ACTIVO")
    private String estado;
}
