package com.vegastore.jitarger.dto.base;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductoDTO {

    @Positive(message = "El id del producto no puede ser negativo")
    @Schema(description = "Identificador del producto", example = "1")
    private long id;

    @NotBlank(message = "El nombre del producto no puede estar en blanco")
    @Length(min = 3, max = 50, message = "El nombre del producto debe tener entre 3 y 50 caracteres")
    @Schema(description = "Nombre del producto", example = "Mesa")
    private String nombre;

    @NotBlank(message = "La descripción del producto no puede estar en blanco")
    @Length(min = 3, max = 255, message = "La descripción del producto debe tener entre 3 y 255 caracteres") 
    @Schema(description = "Descripción del producto", example = "Una mesa de madera")
    private String descripcion;

    @Positive(message = "El id de la categoria no puede ser negativo")
    @Schema(description = "Identificador de la categoria", example = "1")
    private long idCategoria;

    @Positive(message = "El id de la subcategoria no puede ser negativo")
    @Schema(description = "Identificador de la subcategoria", example = "1")
    private long idSubCategoria;

    @NotBlank(message = "El material del producto no puede estar en blanco")
    @Length(min = 3, max = 20, message = "El estado del producto debe tener entre 3 y 20 caracteres")
    @Schema(description = "Estado del producto", example = "1")
    private String estado;

    @NotBlank(message = "El material del producto no puede estar en blanco")
    @Length(min = 3, max = 50, message = "El tipo de prenda del producto debe tener entre 3 y 50 caracteres")
    @Schema(description = "Tipo de prenda del producto", example = "Camiseta")
    private String tipoPrenda;

    @NotBlank(message = "El material del producto no puede estar en blanco")
    @Length(min = 3, max = 50, message = "El marca del producto debe tener entre 3 y 50 caracteres")
    @Schema(description = "Marca del producto", example = "Nike")
    private String marca;

    @NotBlank(message = "El material del producto no puede estar en blanco")
    @Length(min = 3, max = 50, message = "El talla del producto debe tener entre 3 y 50 caracteres")
    @Schema(description = "Talla del producto", example = "M")
    private String talla;

    @NotBlank(message = "El material del producto no puede estar en blanco")
    @Length(min = 3, max = 50, message = "El color del producto debe tener entre 3 y 50 caracteres")
    @Schema(description = "Color del producto", example = "Rojo")
    private String color;

    @NotBlank(message = "El material del producto no puede estar en blanco")
    @Length(min = 3, max = 50, message = "El genero del producto debe tener entre 3 y 50 caracteres")
    @Schema(description = "Genero del producto", example = "Masculino")
    private String genero;

    @NotBlank(message = "El material del producto no puede estar en blanco")
    @Length(min = 3, max = 50, message = "El material del producto debe tener entre 3 y 50 caracteres")
    @Schema(description = "Material del producto", example = "Lana")
    private String material;

    @Length(min = 3, max = 50, message = "El temporada del producto debe tener entre 3 y 50 caracteres")
    @Schema(description = "Temporada del producto", example = "primavera")
    @Null
    private String temporada;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Fecha de creación del producto", example = "2023-05-01T00:00:00")
    private LocalDateTime fechaRegistro;
    
}
