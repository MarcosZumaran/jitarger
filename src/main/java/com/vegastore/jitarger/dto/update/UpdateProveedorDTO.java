package com.vegastore.jitarger.dto.update;

import org.hibernate.validator.constraints.Length;
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
public class UpdateProveedorDTO {

    @Length(min = 3, max = 50, message = "El nombre del proveedor debe tener entre 3 y 50 caracteres")
    @Schema(description = "Nombre del proveedor", example = "Mesa")
    private String nombreEmpresa;

    @Length(min = 10, max = 13, message = "El RUC del proveedor debe tener entre 10 y 13 caracteres")
    @Schema(description = "RUC del proveedor", example = "12345678901")
    private String ruc;

    @Length(min = 3, max = 100, message = "La dirección del proveedor debe tener entre 3 y 100 caracteres")
    @Schema(description = "Dirección del proveedor", example = "Calle 123")
    private String direccion;

    @Length(min = 10, max = 20, message = "El telefono del proveedor debe tener entre 10 y 20 caracteres")
    @Pattern(regexp = "^[0-9]{9,20}$", message = "El teléfono debe tener entre 9 y 20 dígitos")
    @Schema(description = "Telefono del proveedor", example = "1234567890")
    private String telefono;

    @Email(message = "El correo no es válido")
    @Length(min = 5, max = 100, message = "El correo del proveedor debe tener entre 5 y 100 caracteres")
    @Schema(description = "Correo electrónico del proveedor", example = "proveedor@gmail.com")
    private String correo;

    @Schema(description = "Estado del proveedor", example = "true")
    private Boolean activo;

}
