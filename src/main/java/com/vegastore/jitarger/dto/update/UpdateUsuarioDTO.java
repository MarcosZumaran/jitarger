package com.vegastore.jitarger.dto.update;

import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder    
@Data
public class UpdateUsuarioDTO {

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

    @Nullable
    @Schema(description = "Clave del usuario", example = "906533211")
    private String clave;

    @Nullable
    @Schema(description = "Rol del usuario", example = "cliente")
    private String rol;

}
