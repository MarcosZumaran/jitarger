package com.vegastore.jitarger.dto.base;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder    
@Data
public class UsuarioDTO {

    @Positive(message = "El id del usuario no puede ser negativo")
    @NotNull(message = "El id del usuario no puede estar en blanco")
    @Schema(description = "Identificador del usuario", example = "1")
    private long id;

    @NotBlank(message = "El nombre del usuario no puede estar en blanco")
    @Schema(description = "Nombre del usuario", example = "Mesa")
    private String nombre;

    @NotBlank(message = "El apellido del usuario no puede estar en blanco")
    @Schema(description = "Apellido del usuario", example = "Arbelas")
    private String apellido;

    @NotBlank(message = "El correo del usuario no puede estar en blanco")
    @Email(message = "El correo no es válido")
    @Schema(description = "Correo electrónico del usuario", example = "mesa@gmail.com")
    private String correo;

    @NotBlank(message = "El telefono del usuario no puede estar en blanco")
    @Pattern(regexp = "^[0-9]{9,20}$", message = "El teléfono debe tener entre 9 y 20 dígitos")
    @Schema(description = "Telefono del usuario", example = "1234567890")
    private String telefono;

    @NotBlank(message = "La clave del usuario no puede estar en blanco")
    @Size(min = 8, max = 50, message = "La clave debe tener entre 8 y 50 caracteres")
    @Schema(description = "Clave del usuario", example = "906533211")
    private String clave;

    @NotBlank(message = "El rol del usuario no puede estar en blanco")
    @Schema(description = "Rol del usuario", example = "cliente")
    private String rol;

    @NotNull(message = "El fecha de creación del usuario no puede estar en blanco")
    @Schema(description = "Fecha de creación del usuario", example = "2023-05-01T00:00:00")
    private String fechaRegistro;

    @NotNull(message = "El fecha de actualización del usuario no puede estar en blanco")
    @Schema(description = "Fecha de actualización del usuario", example = "2023-05-01T00:00:00")
    private String fechaActualizacion;
    
}
