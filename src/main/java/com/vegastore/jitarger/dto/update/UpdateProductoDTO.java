package com.vegastore.jitarger.dto.update;

import org.hibernate.validator.constraints.Length;

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
public class UpdateProductoDTO {

    @Length(min = 3, max = 50, message = "El nombre del producto debe tener entre 3 y 50 caracteres")
    @Schema(description = "Nombre del producto", example = "Mesa")
    private String nombre;

    @Length(min = 3, max = 255, message = "La descripción del producto debe tener entre 3 y 255 caracteres") 
    @Schema(description = "Descripción del producto", example = "Una mesa de madera")
    private String descripcion;

    @Positive(message = "El id de la categoria no puede ser negativo")
    @Schema(description = "Identificador de la categoria", example = "1")
    private Long idCategoria;

    @Positive(message = "El id de la subcategoria no puede ser negativo")
    @Schema(description = "Identificador de la subcategoria", example = "1")
    private Long idSubCategoria;

    @Length(min = 3, max = 20, message = "El estado del producto debe tener entre 3 y 20 caracteres")
    @Schema(description = "Estado del producto", example = "1")
    private String estado;

    @Length(min = 3, max = 50, message = "El tipo de prenda del producto debe tener entre 3 y 50 caracteres")
    @Schema(description = "Tipo de prenda del producto", example = "Camiseta")
    private String tipoPrenda;

    @Length(min = 3, max = 50, message = "El marca del producto debe tener entre 3 y 50 caracteres")
    @Schema(description = "Marca del producto", example = "Nike")
    private String marca;

    @Length(min = 3, max = 50, message = "El talla del producto debe tener entre 3 y 50 caracteres")
    @Schema(description = "Talla del producto", example = "M")
    private String talla;

    @Length(min = 3, max = 50, message = "El color del producto debe tener entre 3 y 50 caracteres")
    @Schema(description = "Color del producto", example = "Rojo")
    private String color;

    @Length(min = 3, max = 50, message = "El genero del producto debe tener entre 3 y 50 caracteres")
    @Schema(description = "Genero del producto", example = "Masculino")
    private String genero;

    @Length(min = 3, max = 50, message = "El material del producto debe tener entre 3 y 50 caracteres")
    @Schema(description = "Material del producto", example = "Lana")
    private String material;

    @Length(min = 3, max = 50, message = "El temporada del producto debe tener entre 3 y 50 caracteres")
    @Schema(description = "Temporada del producto", example = "primavera")
    private String temporada;
    
}
