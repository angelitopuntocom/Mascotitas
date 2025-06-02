package com.mascotitas.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "citas")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numeroCita;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, unique = true)
    private Date fechaHora;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_mascota")
    private Mascota mascota;

    @ManyToOne
    @JoinColumn(name = "id_veterinario")
    private Veterinario veterinario;

    @ManyToOne
    @JoinColumn(name = "id_asistente")
    private Asistente asistente;

    @Column(nullable = false)
    private String descripcion;

    @ManyToMany
    @JoinTable(
        name = "cita_paquetes",
        joinColumns = @JoinColumn(name = "id_cita"),
        inverseJoinColumns = @JoinColumn(name = "id_paquete")
    )
    private List<Paquete> paquetes = new ArrayList<>();

    public Cita() {}

    public Cita(Date fechaHora, Cliente cliente, Mascota mascota, String descripcion) {
        this.fechaHora = fechaHora;
        this.cliente = cliente;
        this.mascota = mascota;
        this.descripcion = descripcion;
    }

    // Getters y setters
    public int getNumeroCita() { return numeroCita; }
    public Date getFechaHora() { return fechaHora; }
    public void setFechaHora(Date fechaHora) { this.fechaHora = fechaHora; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Mascota getMascota() { return mascota; }
    public void setMascota(Mascota mascota) { this.mascota = mascota; }
    public Veterinario getVeterinario() { return veterinario; }
    public void setVeterinario(Veterinario veterinario) { this.veterinario = veterinario; }
    public Asistente getAsistente() { return asistente; }
    public void setAsistente(Asistente asistente) { this.asistente = asistente; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public List<Paquete> getPaquetes() { return paquetes; }
    public void setPaquetes(List<Paquete> paquetes) { this.paquetes = paquetes; }
    public void agregarPaquete(Paquete p) { this.paquetes.add(p); }

    @Override
    public String toString() {
        return "Cita #" + numeroCita + " - " + fechaHora + " - Cliente: " + cliente + " - Mascota: " + mascota;
    }
}
