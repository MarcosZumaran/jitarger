package com.vegastore.jitarger.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vegastore.jitarger.responce.ApiResponse;

import jakarta.validation.ConstraintViolationException;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador de excepciones globales para la API.
 * 
 * @author Alalsocram2007
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Maneja la excepción {@link RecursoNoEncontradoException}.
     * 
     * @param ex la excepción
     * @return una respuesta con el estado HTTP 404 (Not Found)
     */
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(RecursoNoEncontradoException ex) {
        logger.warn("Recurso no encontrado: {}", ex.getMessage());
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja la excepción {@link EmptyResultDataAccessException}.
     * 
     * @param ex la excepción
     * @return una respuesta con el estado HTTP 404 (Not Found)
     */
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ApiResponse<Object>> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
        logger.warn("Consulta vacía: {}", ex.getMessage());
        return new ResponseEntity<>(ApiResponse.error("El recurso solicitado no existe"), HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja la excepción {@link DataAccessException}.
     * 
     * @param ex la excepción
     * @return una respuesta con el estado HTTP 500 (Internal Server Error)
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataAccessException(DataAccessException ex) {
        logger.error("Error de acceso a datos", ex);
        return new ResponseEntity<>(ApiResponse.error("Error de acceso a datos: " + ex.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Maneja la excepción {@link MethodArgumentNotValidException}.
     * 
     * @param ex la excepción
     * @return una respuesta con el estado HTTP 400 (Bad Request)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logger.warn("Error de validación: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(ApiResponse.validationError("Error de validación", errors), HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja todas las excepciones que no tienen un manejador específico.
     * 
     * @param ex la excepción
     * @return una respuesta con el estado HTTP 500 (Internal Server Error)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGlobalException(Exception ex) {
        logger.error("Error inesperado", ex);
        return new ResponseEntity<>(ApiResponse.error("Error interno del servidor: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Maneja la excepción {@link RecursoDuplicadoException}.
     * 
     * @param ex la excepción
     * @return una respuesta con el estado HTTP 409 (Conflict)
     */
    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicateResourceException(RecursoDuplicadoException ex) {
        logger.warn("Recurso duplicado: {}", ex.getMessage());
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleConstraintViolation(ConstraintViolationException ex) { 
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(field, message);
        });
        logger.warn("Error de restricción: {}", ex.getMessage());
        return new ResponseEntity<ApiResponse<Map<String, String>>>(
            ApiResponse.validationError("Error de restricción", errors),
            HttpStatus.BAD_REQUEST
        );
    }


}
