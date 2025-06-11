package com.vegastore.jitarger.dto.create;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

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
public class CreateLoteDTO {

    @Positive(message = "El id del producto no puede ser negativo")
    @NotNull(message = "El id del producto no puede ser nulo")
    @Schema(description = "Identificador del producto", example = "1")
    private long idProducto;

    @Positive(message = "El id del proveedor no puede ser negativo")
    @NotNull(message = "El id del proveedor no puede ser nulo")
    @Schema(description = "Identificador del proveedor", example = "1")
    private long idProveedor;

    @NotBlank(message = "La unidad de medida del lote no puede estar en blanco")
    @Length(min = 1, max = 20, message = "La unidad de medida del lote debe tener entre 1 y 20 caracteres")
    @Schema(description = "Unidad de medida del lote", example = "DOCENAS")
    private String unidadMedidaBase;

    @NotBlank(message = "La abreviatura de la unidad de medida del lote no puede estar en blanco")
    @Length(min = 1, max = 6, message = "La abreviatura de la unidad de medida del lote debe tener entre 1 y 20 caracteres")
    @Schema(description = "Abreviatura de la unidad de medida del lote", example = "DOC")
    private String unidadMedidaAbreviatura;

    @Positive(message = "El costo del lote no puede ser negativo")
    @NotNull(message = "El costo del lote no puede ser nulo")
    @Schema(description = "Costo del lote", example = "3.99")
    private BigDecimal costo;

    @Positive(message = "La cantidad del lote no puede ser negativa")
    @NotNull(message = "La cantidad del lote no puede ser nula")
    @Schema(description = "Cantidad del lote", example = "3.250")
    private BigDecimal cantidadInicial;

    @Positive(message = "El stock del lote no puede ser negativo")
    @NotNull(message = "El stock del lote no puede ser nulo")
    @Schema(description = "Disponibles del lote", example = "3.250")    
    private BigDecimal stock;
}
