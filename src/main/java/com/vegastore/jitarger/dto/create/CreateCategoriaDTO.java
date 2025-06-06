
package com.vegastore.jitarger.dto.create;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateCategoriaDTO {

    @NotBlank(message = "El nombre de la categoria no puede estar en blanco")
    @Length(min = 3, max = 50, message = "El nombre de la categoria debe tener entre 3 y 50 caracteres")
    @Schema(description = "Nombre de la categoria")
    private String nombre;

    @NotBlank(message = "La descripción de la categoria no puede estar en blanco")
    @Length(min = 3, max = 160, message = "La descripción de la categoria debe tener entre 3 y 160 caracteres")
    @Schema(description = "Descripción de la categoria")
    private String descripcion;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Fecha de registro de la categoria", example = "2023-10-01T12:00:00")
    private LocalDateTime fechaRegistro;
    
}
