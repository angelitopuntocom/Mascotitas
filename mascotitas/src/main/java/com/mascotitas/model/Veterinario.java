package com.mascotitas.model;

import jakarta.persistence.*;
import java.util.Date;

/*
 * Clase: Veterinario
 * Hereda de Persona, representa a un veterinario con cédula profesional
 */
@Entity
@Table(name = "veterinarios")
public class Veterinario extends Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVeterinario;

    @Column(nullable = false, unique = true)
    private int cedula;

    public Veterinario() {
        super();
    }

    public Veterinario(String nombre, String paterno, String materno, Date fechaNacimiento, String curp, int cedula) {
        super(nombre, paterno, materno, fechaNacimiento, curp);
        this.cedula = cedula;
    }

    public int getIdVeterinario() { return idVeterinario; }

    public int getCedula() { return cedula; }
    public void setCedula(int cedula) { this.cedula = cedula; }

    @Override
    public String toString() {
        return "Dr. " + super.toString() + " - Cédula: " + cedula;
    }
}
