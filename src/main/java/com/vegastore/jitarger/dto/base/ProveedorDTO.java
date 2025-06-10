package com.vegastore.jitarger.dto.base;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder    
@Data
public class ProveedorDTO {

    @Positive(message = "El id del proveedor no puede ser negativo")
    @NotNull(message = "El id del proveedor no puede estar en blanco")
    @Schema(description = "Identificador del proveedor", example = "1")
    private long id;

    @NotBlank(message = "El nombre del proveedor no puede estar en blanco")
    @Length(min = 3, max = 50, message = "El nombre del proveedor debe tener entre 3 y 50 caracteres")
    @Schema(description = "Nombre del proveedor", example = "Mesa")
    private String nombreEmpresa;

    @NotBlank(message = "La RUC del proveedor no puede estar en blanco")
    @Length(min = 10, max = 13, message = "El RUC del proveedor debe tener entre 10 y 13 caracteres")
    @Schema(description = "RUC del proveedor", example = "12345678901")
    private String ruc;

    @NotBlank(message = "La dirección del proveedor no puede estar en blanco")
    @Length(min = 3, max = 100, message = "La dirección del proveedor debe tener entre 3 y 100 caracteres")
    @Schema(description = "Dirección del proveedor", example = "Calle 123")
    private String direccion;

    @NotBlank(message = "El telefono del proveedor no puede estar en blanco")
    @Length(min = 9, max = 20, message = "El telefono del proveedor debe tener entre 9 y 20 caracteres")
    @Pattern(regexp = "^[0-9]{9,20}$", message = "El teléfono debe tener entre 9 y 20 dígitos")
    @Schema(description = "Telefono del proveedor", example = "1234567890")
    private String telefono;

    @NotBlank(message = "La correo electrónico del proveedor no puede estar en blanco")
    @Email(message = "El correo no es válido")
    @Length(min = 5, max = 100, message = "El correo del proveedor debe tener entre 5 y 100 caracteres")
    @Schema(description = "Correo electrónico del proveedor", example = "proveedor@gmail.com")
    private String correo;

    @NotNull(message = "El fecha de creación del proveedor no puede estar en blanco")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Fecha de creación del proveedor", example = "2023-05-01T00:00:00")
    private LocalDateTime fechaRegistro;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Fecha de actualización del proveedor", example = "2023-05-01T00:00:00")
    private LocalDateTime fechaActualizacion;

    @NotNull(message = "El estado del proveedor no puede estar en blanco")
    @Schema(description = "Estado del proveedor", example = "true")
    private boolean activo;
    
}
