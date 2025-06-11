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
public class UpdateDetallePedidoDTO {

    @Positive(message = "El id del producto_presentacion no puede ser negativo")
    @Schema(description = "Identificador del producto_presentacion", example = "1")
    private Long idProductoPresentacion;

    @Positive(message = "El id del lote no puede ser negativo")
    @Schema(description = "Identificador del lote", example = "1")
    private Long idLote;

    @Positive(message = "La cantidad del detalle del pedido no puede ser negativa")
    @Schema(description = "Cantidad del detalle del pedido", example = "3.99")
    private BigDecimal cantidad;

    @Positive(message = "El precio unitario del detalle del pedido no puede ser negativo")
    @Schema(description = "Precio unitario del detalle del pedido", example = "3.99")
    private BigDecimal precioUnitario;

    @Positive(message = "El precio total del detalle del pedido no puede ser negativo")
    @Schema(description = "Precio total del detalle del pedido", example = "3.99")
    private BigDecimal subTotal;

    @Schema(description = "Nombre del producto", example = "Producto 1")
    private String nombreProducto;

    @Schema(description = "Unidad de medida del producto", example = "DOC")
    private String unidadmedidaPresentacion;
    
}