package com.vegastore.jitarger.dto.base;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {

    @Positive(message = "El id de la categoría debe ser un número positivo")
    @Schema(description = "Identificador único de la categoría", example = "1")
    private long id;

    @NotBlank(message = "El nombre de la categoría no puede estar en blanco")
    @Length(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    @Schema(description = "Nombre de la categoría", example = "Electrónica")
    private String nombre;

    @NotBlank(message = "La descripción de la categoría no puede estar en blanco")
    @Length(min = 3, max = 160, message = "La descripción debe tener entre 3 y 160 caracteres")
    @Schema(description = "Descripción de la categoría", example = "Productos de tecnología y dispositivos electrónicos")
    private String descripcion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Fecha de creación de la categoría", example = "2025-06-10 14:30:00")
    private LocalDateTime fechaRegistro;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Fecha de última actualización", example = "2025-06-11 09:15:00")
    private LocalDateTime fechaActualizacion;

    @Schema(description = "Estado de la categoría: true si está activa, false si está inactiva", example = "true")
    private boolean activa;
}
