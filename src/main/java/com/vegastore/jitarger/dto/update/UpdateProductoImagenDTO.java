package com.vegastore.jitarger.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateProductoImagenDTO {

    @NotNull(message = "El url del producto imagen no puede ser nulo")
    @Schema(description = "Url del producto imagen", example = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png")
    private String url;

    @NotNull(message = "El tipo del producto imagen no puede ser nulo")
    @Schema(description = "Tipo del producto imagen", example = "imagen")
    private String tipo;
    
}
