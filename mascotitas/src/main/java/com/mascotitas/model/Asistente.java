package com.mascotitas.model;

import jakarta.persistence.*;
import java.util.Date;

/*
 * Clase: Asistente
 * Hereda de Persona, representa un asistente personal
 */
@Entity
@Table(name = "asistentes")
public class Asistente extends Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAsistente;

    public Asistente() {
        super();
    }

    public Asistente(String nombre, String paterno, String materno, Date fechaNacimiento, String curp) {
        super(nombre, paterno, materno, fechaNacimiento, curp);
    }

    public int getIdAsistente() { return idAsistente; }

    @Override
    public String toString() {
        return "Asistente " + super.toString();
    }
}
