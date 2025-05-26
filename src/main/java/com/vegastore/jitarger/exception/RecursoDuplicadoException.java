package com.vegastore.jitarger.exception;

/**
 * Excepcion que se lanza cuando se intenta guardar un recurso que ya existe.
 * 
 * @author Alalsocram2007
 */
public class RecursoDuplicadoException extends RuntimeException {

    /**
     * Constructor que recibe el mensaje de la excepcion.
     * 
     * @param mensaje El mensaje de la excepcion.
     */
    public RecursoDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
