package com.vegastore.jitarger.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateCarritoDTO {

    @NotNull(message = "El estado del carrito no puede estar en blanco")
    @Schema(description = "Estado del carrito", example = "ACTIVO")
    private String estado;

}
