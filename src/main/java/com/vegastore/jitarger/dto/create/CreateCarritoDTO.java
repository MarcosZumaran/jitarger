package com.vegastore.jitarger.dto.create;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateCarritoDTO {

    @Positive(message = "El id del usuario no puede ser negativo")
    @NotNull(message = "El id del usuario no puede ser nulo")
    @Schema(description = "Identificador del usuario", example = "1")
    private long idUsuario;

    @NotNull(message = "La fecha de creación del carrito no puede estar en blanco")
    @Schema(description = "Fecha de creación del carrito")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCreacion;


}
