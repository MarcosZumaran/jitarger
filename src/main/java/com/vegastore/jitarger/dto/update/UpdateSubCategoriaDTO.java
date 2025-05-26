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
public class UpdateSubCategoriaDTO {

    @NotNull(message = "El nombre de la subcategoria no puede estar en blanco")
    @Schema(description = "Nombre de la subcategoria", example = "Mesa")
    private String nombre;

    @NotNull(message = "La descripción de la subcategoria no puede estar en blanco")
    @Schema(description = "Descripción de la subcategoria", example = "Una mesa de madera")
    private String descripcion;

}
