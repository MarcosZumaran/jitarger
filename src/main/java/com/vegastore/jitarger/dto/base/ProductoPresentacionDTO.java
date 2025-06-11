package com.vegastore.jitarger.dto.base;

import java.math.BigDecimal;
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
@Builder
@Data
public class ProductoPresentacionDTO {

    @Positive(message = "El id de la presentación del producto no puede ser negativo")
    @Schema(description = "Identificador de la presentación del producto", example = "1")
    private long id;

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
    private BigDecimal equivalencia;

    @Positive(message = "El precio de la presentación del producto no puede ser negativo")
    @Schema(description = "Precio de la presentación del producto", example = "12.50")
    private BigDecimal precio;

    @Schema(description = "Indica si la presentación del producto esta disponible", example = "true")
    private boolean disponible;

    @Schema(description = "Fecha de creación de la presentación del producto", example = "2022-01-01T00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaCreacion;

    @Schema(description = "Fecha de actualización de la presentación del producto", example = "2022-01-01T00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaActualizacion;
    
}
