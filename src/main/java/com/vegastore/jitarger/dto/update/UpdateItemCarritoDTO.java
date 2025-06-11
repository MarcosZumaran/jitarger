package com.vegastore.jitarger.dto.update;

import java.math.BigDecimal;

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
public class UpdateItemCarritoDTO {

    @Positive(message = "El id del producto presentacion no puede ser negativo")
    @Schema(description = "Identificador del producto", example = "1")
    private long idProductoPresentacion;

    @Positive(message = "El cantidad no puede ser negativo")
    @Schema(description = "Cantidad del item del carrito", example = "1")    
    private BigDecimal cantidad;

    @Positive(message = "El precio actual del item del carrito no puede ser negativo")
    @Schema(description = "Precio actual del item del carrito", example = "1")
    private BigDecimal precioActual;

    @Schema(description = "Nombre del producto", example = "Producto 1")
    private String nombreProducto;

    @Schema(description = "Unidad de medida del producto", example = "DOCENA")
    private String unidadmedidaPresentacion;

    @Schema(description = "Estado del item del carrito", example = "true")
    private Boolean activo;
    
}
