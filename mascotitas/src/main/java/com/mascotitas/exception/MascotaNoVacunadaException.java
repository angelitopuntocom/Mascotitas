package com.mascotitas.exception;

/*
 * Excepción: MascotaNoVacunadaException
 * Se lanza cuando una mascota no tiene ninguna vacuna registrada
 */
public class MascotaNoVacunadaException extends Exception {
    public MascotaNoVacunadaException(String mensaje) {
        super(mensaje);
    }
}
