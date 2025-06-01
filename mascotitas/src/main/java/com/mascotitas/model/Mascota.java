package com.mascotitas.model;

import jakarta.persistence.*;
import java.util.*;

/*
 * Clase: Mascota
 * Representa una mascota del cliente, con nombre, raza, vacunas, y su dueño
 */
@Entity
@Table(name = "mascotas")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numeroMascota;

    @Column(nullable = false)
    private String nombre;

    private String raza;

    @Column
    private String tipo;

    @Column
    private int edad;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "vacunas", joinColumns = @JoinColumn(name = "numero_mascota"))
    @Column(name = "nombre_vacuna")
    private List<String> vacunas = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    public Mascota() {}

    public Mascota(String nombre, String raza) {
        this.nombre = nombre;
        this.raza = raza;
    }

    public int getNumeroMascota() { return numeroMascota; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public List<String> getVacunas() { return vacunas; }
    public void setVacunas(List<String> vacunas) { this.vacunas = vacunas; }

    public void agregarVacuna(String vacuna) {
        if (!vacunas.contains(vacuna)) vacunas.add(vacuna);
    }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    @Override
    public String toString() {
        return nombre + " (" + raza + ") - #" + numeroMascota;
    }
}
