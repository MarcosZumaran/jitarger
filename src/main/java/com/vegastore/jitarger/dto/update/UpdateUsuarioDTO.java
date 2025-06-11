package com.vegastore.jitarger.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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

    @Schema(description = "Nombre del usuario", example = "Mesa")
    private String nombre;

    @Schema(description = "Apellido del usuario", example = "Arbelas")
    private String apellido;

    @Email(message = "El correo no es válido")
    @Schema(description = "Correo electrónico del usuario", example = "mesa@gmail.com")
    private String correo;

    @Pattern(regexp = "^[0-9]{9,20}$", message = "El teléfono debe tener entre 9 y 20 dígitos")
    @Schema(description = "Telefono del usuario", example = "1234567890")
    private String telefono;

    @Schema(description = "Clave del usuario", example = "906533211")
    private String clave;

    @Schema(description = "Rol del usuario", example = "cliente")
    private String rol;

    @Schema(description = "Estado del usuario", example = "ACTIVO")
    private Boolean activo;

}
