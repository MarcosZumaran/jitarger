package com.vegastore.jitarger.dto.base;

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
public class ProductoImagenDTO {

    @Positive(message = "El id del producto imagen no puede ser negativo")
    @Schema(description = "Identificador del producto imagen", example = "1")
    private long id;

    @NotNull(message = "El producto no puede ser nulo")
    @Schema(description = "Identificador del producto", example = "1")
    private long idProducto;

    @NotNull(message = "El url del producto imagen no puede ser nulo")
    @Schema(description = "Url del producto imagen", example = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png")
    private String url;

    @NotNull(message = "El tipo del producto imagen no puede ser nulo")
    @Schema(description = "Tipo del producto imagen", example = "imagen")
    private String tipo;
    
}
