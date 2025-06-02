package com.mascotitas.service;

import java.util.Date;

public interface RevisionDeCitas {
    boolean asistenteDisponible(String nombre, String paterno, String materno);
    boolean veterinarioDisponible(String nombre, String paterno, String materno);
    boolean mascotaVacunada(int numeroMascota);
    boolean revisarDisponibilidad(Date fechaCita);
}
