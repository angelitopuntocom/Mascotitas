package com.mascotitas.model;

import jakarta.persistence.*;
import java.util.Date;

/*
 * Clase: Persona
 * Superclase abstracta para Cliente, Veterinario, Asistente y Gerente
 */
@MappedSuperclass
public abstract class Persona {

    @Column(nullable = false)
    protected String nombre;

    protected String paterno;
    protected String materno;

    @Temporal(TemporalType.DATE)
    protected Date fechaNacimiento;

    @Column(length = 18, unique = true)
    protected String curp;

    public Persona() {
    }

    public Persona(String nombre, String paterno, String materno, Date fechaNacimiento, String curp) {
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.fechaNacimiento = fechaNacimiento;
        this.curp = curp;
    }

    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPaterno() { return paterno; }
    public void setPaterno(String paterno) { this.paterno = paterno; }

    public String getMaterno() { return materno; }
    public void setMaterno(String materno) { this.materno = materno; }

    public Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getCurp() { return curp; }
    public void setCurp(String curp) { this.curp = curp; }

    @Override
    public String toString() {
        return nombre + " " + (paterno != null ? paterno : "") + " " + (materno != null ? materno : "");
    }
}
