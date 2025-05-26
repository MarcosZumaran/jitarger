package com.vegastore.jitarger.exception;

import lombok.Getter;

/**
 * Excepcion que se lanza cuando se intenta obtener un recurso que no existe en la base de datos.
 * 
 * @author Alalsocram2007
 */
@Getter
public class RecursoNoEncontradoException extends RuntimeException {
    private final String recurso;
    private final String campo;
    private final Object valor;

    /**
     * Constructor de la excepcion.
     * 
     * @param recurso  el nombre del recurso que se intenta obtener.
     * @param campo    el nombre del campo que se esta intentando obtener.
     * @param valor    el valor del campo que se esta intentando obtener.
     */
    public RecursoNoEncontradoException(String recurso, String campo, Object valor) {
        super(String.format("%s no encontrado con %s: '%s'", recurso, campo, valor));
        this.recurso = recurso;
        this.campo = campo;
        this.valor = valor;
    }
}
