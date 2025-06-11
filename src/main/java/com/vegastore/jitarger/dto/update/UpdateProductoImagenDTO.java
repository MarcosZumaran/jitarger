package com.vegastore.jitarger.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateProductoImagenDTO {

    @Schema(description = "Url del producto imagen", example = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png")
    private String url;

    @Schema(description = "Tipo del producto imagen", example = "imagen")
    private String tipo;
    
}
