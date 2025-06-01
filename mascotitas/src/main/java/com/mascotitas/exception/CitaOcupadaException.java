package com.mascotitas.exception;

/*
 * Excepción: CitaOcupadaException
 * Se lanza cuando se intenta agendar una cita en una hora ya ocupada
 */
public class CitaOcupadaException extends Exception {
    public CitaOcupadaException(String mensaje) {
        super(mensaje);
    }
}
