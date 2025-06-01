package com.mascotitas.model;

import jakarta.persistence.*;
import java.util.*;

/*
 * Clase: Cita
 * Representa una cita médica o de servicio para una mascota
 */
@Entity
@Table(name = "citas", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"fechaHora"})
})
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fechaHora;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_mascota", nullable = false)
    private Mascota mascota;

    @ManyToOne
    @JoinColumn(name = "id_veterinario", nullable = true)
    private Veterinario veterinario;

    @ManyToOne
    @JoinColumn(name = "id_asistente", nullable = true)
    private Asistente asistente;

    @Column(length = 300)
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

    public int getId() { return id; }

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

    public void agregarPaquete(Paquete paquete) {
        paquetes.add(paquete);
    }

    @Override
    public String toString() {
        return "Cita #" + id + " | Fecha: " + fechaHora + " | Cliente: " + cliente.getNombre() +
               " | Mascota: " + mascota.getNombre();
    }
}
