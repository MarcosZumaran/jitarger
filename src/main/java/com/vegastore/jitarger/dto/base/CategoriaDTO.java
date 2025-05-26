
package com.vegastore.jitarger.dto.base;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CategoriaDTO {

    @Positive(message = "El id de la categoria no puede ser negativo")
    @Schema(description = "Identificador de la categoria", example = "1")
    private long id;

    @NotBlank(message = "El nombre de la categoria no puede estar en blanco")
    @Length(min = 3, max = 50, message = "El nombre de la categoria debe tener entre 3 y 50 caracteres")
    @Schema(description = "Nombre de la categoria")
    private String nombre;

    @NotBlank(message = "La descripción de la categoria no puede estar en blanco")
    @Length(min = 3, max = 160, message = "La descripción de la categoria debe tener entre 3 y 160 caracteres")
    @Schema(description = "Descripción de la categoria")
    private String descripcion;
    
}
