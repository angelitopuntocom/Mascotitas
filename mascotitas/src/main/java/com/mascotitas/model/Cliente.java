package com.mascotitas.model;

import jakarta.persistence.*;

import java.beans.Transient;
import java.util.*;

/*
 * Clase: Cliente
 * Hereda de Persona, representa al cliente que puede tener mascotas y tarjeta
 */
@Entity
@Table(name = "clientes")
public class Cliente extends Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numeroCliente;

    @Column(length = 20)
    private String telefono; // ✅ Aquí se declara
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Mascota> mascotas = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tarjeta")
    private Tarjeta tarjeta;

    public Cliente() {
        super();
    }

    public Cliente(String nombre, String paterno, String materno, Date fechaNacimiento, String curp) {
        super(nombre, paterno, materno, fechaNacimiento, curp);
    }


    // ✅ Getters y setters para teléfono
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroCliente() { return numeroCliente; }

    public List<Mascota> getMascotas() { return mascotas; }
    public void setMascotas(List<Mascota> mascotas) { this.mascotas = mascotas; }

    public void addMascota(Mascota m) {
        mascotas.add(m);
        m.setCliente(this);
    }

    public void removeMascota(Mascota m) {
        mascotas.remove(m);
        m.setCliente(null);
    }

    public Tarjeta getTarjeta() { return tarjeta; }
    public void setTarjeta(Tarjeta tarjeta) { this.tarjeta = tarjeta; }

    @Override
    public String toString() {
        return "Cliente #" + numeroCliente + ": " + super.toString();
    }
}
