package com.vegastore.jitarger.dto.update;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

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
public class UpdateLoteDTO {

    @NotNull(message = "La unidad de medida del lote no puede ser nula")
    @Length(min = 3, max = 20, message = "La unidad de medida del lote debe tener entre 3 y 20 caracteres")
    @Schema(description = "Unidad de medida del lote", example = "DOCENAS")
    private String unidadMedidaBase;

    @NotNull(message = "La abreviatura de la unidad de medida del lote no puede ser nula")
    @Length(min = 1, max = 6, message = "La abreviatura de la unidad de medida del lote debe tener entre 1 y 20 caracteres")
    @Schema(description = "Abreviatura de la unidad de medida del lote", example = "DOC")
    private String unidadMedidaAbreviatura;

    @Positive(message = "El costo del lote no puede ser negativo")
    @Schema(description = "Costo del lote", example = "3.99")
    private BigDecimal costo;

    @Positive(message = "La cantidad del lote no puede ser negativa")
    @Schema(description = "Cantidad del lote", example = "3.250")
    private BigDecimal cantidadInicial;

    @Positive(message = "El disponibles del lote no puede ser negativo")
    @Schema(description = "Disponibles del lote", example = "3.250")    
    private BigDecimal stock;

}
