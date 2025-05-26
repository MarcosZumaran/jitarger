package com.vegastore.jitarger.dto.update;

import java.math.BigDecimal;


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
public class UpdateItemCarritoDTO {

    @Positive(message = "El cantidad no puede ser negativo")
    @NotNull(message = "La cantidad no puede ser nula")
    @Schema(description = "Cantidad del item del carrito", example = "1")    
    private BigDecimal cantidad;

    @NotBlank(message = "El nombre del producto no puede estar en blanco")
    @Schema(description = "Nombre del producto", example = "Producto 1")
    private String estado;
    
}
