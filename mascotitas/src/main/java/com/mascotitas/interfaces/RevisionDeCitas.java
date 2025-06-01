package com.mascotitas.interfaces;

import java.util.Date;

public interface RevisionDeCitas {

    /**
     * Verifica si el asistente está disponible por nombre completo.
     */
    boolean asistenteDisponible(String nombre, String paterno, String materno);

    /**
     * Verifica si el veterinario está disponible por nombre completo.
     */
    boolean veterinarioDisponible(String nombre, String paterno, String materno);

    /**
     * Verifica si una mascota tiene al menos una vacuna.
     */
    boolean mascotaVacunada(int numeroMascota);

    /**
     * Verifica si la fecha y hora no está ocupada por otra cita.
     */
    boolean revisarDisponibilidad(Date fechaCita);
}
