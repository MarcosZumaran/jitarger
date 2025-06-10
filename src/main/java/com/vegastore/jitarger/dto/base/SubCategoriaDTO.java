package com.vegastore.jitarger.dto.base;

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
public class SubCategoriaDTO {

    @Positive(message = "El id de la subcategoria no puede ser negativo")
    @NotNull(message = "El id de la subcategoria no puede estar en blanco")
    @Schema(description = "Identificador de la subcategoria", example = "1")
    private long id;

    @Positive(message = "El id de la categoria de la subcategoria no puede ser negativo")
    @NotNull(message = "El id de la categoria de la subcategoria no puede estar en blanco")
    @Schema(description = "Identificador de la categoria de la subcategoria", example = "1")
    private long idCategoria;

    @NotBlank(message = "El nombre de la subcategoria no puede estar en blanco")
    @Schema(description = "Nombre de la subcategoria", example = "Mesa")
    private String nombre;

    @NotBlank(message = "La descripción de la subcategoria no puede estar en blanco")
    @Schema(description = "Descripción de la subcategoria", example = "Una mesa de madera")
    private String descripcion;

    @NotNull(message = "La fecha de creación de la subcategoria no puede estar en blanco")
    @Schema(description = "Fecha de creación de la subcategoria", example = "2023-05-01T00:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaRegistro;

    @Schema(description = "Fecha de actualización de la subcategoria", example = "2023-05-01T00:00:00")
    @NotNull(message = "La fecha de actualización de la subcategoria no puede estar en blanco")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaActualizacion;

    @Schema(description = "Estado de la subcategoria, true si está activa, false si está inactiva", example = "true")
    private boolean activa;
    
}
