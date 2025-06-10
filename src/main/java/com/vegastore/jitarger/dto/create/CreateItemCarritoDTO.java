package com.vegastore.jitarger.dto.create;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class CreateItemCarritoDTO {

    @Positive(message = "El id del carrito no puede ser negativo")
    @NotNull(message = "El id del carrito no puede ser nulo")
    @Schema(description = "Identificador del carrito", example = "1")
    private long idCarrito;

    @Positive(message = "El id del producto presentacion no puede ser negativo")
    @NotNull(message = "El id del producto presentacion no puede ser nulo")
    @Schema(description = "Identificador del producto", example = "1")
    private long idProductoPresentacion;

    @Positive(message = "El cantidad no puede ser negativo")
    @NotNull(message = "La cantidad no puede ser nula")
    @Schema(description = "Cantidad del item del carrito", example = "1")    
    private BigDecimal cantidad;

    @NotNull(message = "La fecha de agregado del item al carrito no puede ser nula")
    @Schema(description = "Fecha de agregado del item al carrito")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaAgregado;

    @Positive(message = "El precio actual del item del carrito no puede ser negativo")
    @NotNull(message = "El precio actual del item del carrito no puede ser nulo")
    @Schema(description = "Precio actual del item del carrito", example = "1")
    private BigDecimal precioActual;

    @NotBlank(message = "El nombre del producto no puede estar en blanco")
    @Schema(description = "Nombre del producto", example = "Producto 1")
    private String nombreProducto;

    @NotBlank(message = "La unidad de medida del producto no puede estar en blanco")
    @Schema(description = "Unidad de medida del producto", example = "DOCENA")
    private String unidadmedidaPresentacion;

    @NotBlank(message = "El estado del item del carrito no puede estar en blanco")
    @Schema(description = "Estado del item del carrito", example = "true")
    private boolean activo;
    
}
