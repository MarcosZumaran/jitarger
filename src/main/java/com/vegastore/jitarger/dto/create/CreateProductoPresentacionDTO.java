package com.vegastore.jitarger.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class CreateProductoPresentacionDTO {

    @NotNull(message = "El producto no puede ser nulo")
    @Schema(description = "Identificador del producto", example = "1")
    private long idProducto;

    @NotNull(message = "La unidad de medida de la presentación del producto no puede ser nula")
    @Schema(description = "Unidad de medida de la presentación del producto", example = "docena")
    private String unidadMedida;

    @NotNull(message = "La abreviatura de la unidad de medida de la presentación del producto no puede ser nula")
    @Schema(description = "Abreviatura de la unidad de medida de la presentación del producto", example = "doc")
    private String unidadAbreviatura;   

    @Positive(message = "El equivalencia de la presentación del producto no puede ser negativo")
    @Schema(description = "Equivalencia de la presentación del producto en referencia a la unidad de medida base del lote", example = "12")
    private int equivalencia;
    
}
