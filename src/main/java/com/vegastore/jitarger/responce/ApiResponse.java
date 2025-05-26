package com.vegastore.jitarger.responce;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa una respuesta a una peticion HTTP. Puede ser un resultado exitoso o
 * un error.
 *
 * @param <T> el tipo de los datos que se est n devolviendo en la respuesta.
 *            Si se est  devolviendo un error, se pueden devolver los mensajes de
 *            error en este campo.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    /**
     * Indica si la respuesta es exitosa o no.
     */
    private boolean exito;

    /**
     * Un mensaje con informaci n adicional sobre la respuesta. Si se est  devolviendo
     * un error, aqui se puede incluir el mensaje de error.
     */
    private String mensaje;

    /**
     * Los datos que se est n devolviendo en la respuesta. Si se est  devolviendo
     * un error, este campo puede ser nulo.
     */
    private T datos;

    /**
     * La fecha y hora en la que se gener  la respuesta.
     */
    private LocalDateTime timestamp;

    /**
     * Crea una respuesta exitosa con los datos especificados.
     *
     * @param data   los datos que se est n devolviendo en la respuesta.
     * @param message un mensaje con informaci n adicional sobre la respuesta.
     * @return una respuesta exitosa con los datos especificados.
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .exito(true)
                .mensaje(message)
                .datos(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Crea una respuesta con un error con el mensaje especificado.
     *
     * @param message el mensaje de error que se va a incluir en la respuesta.
     * @return una respuesta con un error con el mensaje especificado.
     */
    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .exito(false)
                .mensaje(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Crea una respuesta con un error de validaci n con los mensajes de error
     * especificados.
     *
     * @param message el mensaje de error que se va a incluir en la respuesta.
     * @param errors   los mensajes de error de validaci n.
     * @return una respuesta con un error de validaci n con los mensajes de error
     *         especificados.
     */
    public static ApiResponse<Map<String, String>> validationError(String message, Map<String, String> errors) {
        return ApiResponse.<Map<String, String>>builder()
                .exito(false)
                .mensaje(message)
                .datos(errors)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
