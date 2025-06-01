package com.mascotitas.model;

import com.mascotitas.model.enums.Sucursal;
import jakarta.persistence.*;
import java.util.Date;

/*
 * Clase: Gerente
 * Hereda de Persona y representa al gerente de una sucursal
 */
@Entity
@Table(name = "gerentes")
public class Gerente extends Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numeroGerente;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sucursal sucursal;

    public Gerente() {
        super();
    }

    public Gerente(String nombre, String paterno, String materno, Date fechaNacimiento, String curp, Sucursal sucursal) {
        super(nombre, paterno, materno, fechaNacimiento, curp);
        this.sucursal = sucursal;
    }

    public int getNumeroGerente() { return numeroGerente; }

    public Sucursal getSucursal() { return sucursal; }
    public void setSucursal(Sucursal sucursal) { this.sucursal = sucursal; }

    @Override
    public String toString() {
        return "Gerente #" + numeroGerente + " - " + super.toString() + " - Sucursal: " + sucursal;
    }
}
