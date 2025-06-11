package com.vegastore.jitarger.dto.update;

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
public class UpdateSubCategoriaDTO {

    @Positive(message = "El id de la categoria de la subcategoria no puede ser negativo")
    @Schema(description = "Identificador de la categoria de la subcategoria", example = "1")
    private Long idCategoria;

    @Schema(description = "Nombre de la subcategoria", example = "Mesa")
    private String nombre;

    @Schema(description = "Descripción de la subcategoria", example = "Una mesa de madera")
    private String descripcion;

    @Schema(description = "Estado de la subcategoria, true si está activa, false si está inactiva", example = "true")
    private Boolean activa;

}
