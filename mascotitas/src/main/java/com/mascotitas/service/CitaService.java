package com.mascotitas.service;

import com.mascotitas.interfaces.RevisionDeCitas;
import com.mascotitas.model.*;
import com.mascotitas.exception.CitaOcupadaException;
import com.mascotitas.exception.MascotaNoVacunadaException;
import com.mascotitas.exception.AsistenteNoDisponibleException;
import com.mascotitas.exception.VeterinarioNoDisponibleException;

import jakarta.persistence.*;
import java.util.*;

public class CitaService implements RevisionDeCitas {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("mascotitasPU");

    @Override
    public boolean asistenteDisponible(String nombre, String paterno, String materno) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT COUNT(c) FROM Cita c WHERE c.asistente.nombre = :nombre AND " +
                    "(:paterno IS NULL OR c.asistente.paterno = :paterno) AND " +
                    "(:materno IS NULL OR c.asistente.materno = :materno)";
            Long count = em.createQuery(jpql, Long.class)
                .setParameter("nombre", nombre)
                .setParameter("paterno", paterno)
                .setParameter("materno", materno)
                .getSingleResult();
            return count == 0;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean veterinarioDisponible(String nombre, String paterno, String materno) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT COUNT(c) FROM Cita c WHERE c.veterinario.nombre = :nombre AND " +
                    "(:paterno IS NULL OR c.veterinario.paterno = :paterno) AND " +
                    "(:materno IS NULL OR c.veterinario.materno = :materno)";
            Long count = em.createQuery(jpql, Long.class)
                .setParameter("nombre", nombre)
                .setParameter("paterno", paterno)
                .setParameter("materno", materno)
                .getSingleResult();
            return count == 0;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean mascotaVacunada(int numeroMascota) {
        EntityManager em = emf.createEntityManager();
        try {
            Mascota m = em.find(Mascota.class, numeroMascota);
            return m != null && !m.getVacunas().isEmpty();
        } finally {
            em.close();
        }
    }

    @Override
    public boolean revisarDisponibilidad(Date fechaCita) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT COUNT(c) FROM Cita c WHERE c.fechaHora = :fecha";
            Long count = em.createQuery(jpql, Long.class)
                .setParameter("fecha", fechaCita)
                .getSingleResult();
            return count == 0;
        } finally {
            em.close();
        }
    }

    public void agendarCita(Cita cita) throws CitaOcupadaException, MascotaNoVacunadaException, AsistenteNoDisponibleException, VeterinarioNoDisponibleException {
        if (cita.getMascota() == null || cita.getCliente() == null) {
            throw new IllegalArgumentException("La cita debe tener asignada una mascota y un cliente.");
        }

        if (!revisarDisponibilidad(cita.getFechaHora())) {
            throw new CitaOcupadaException("No puede agendar la cita, ya se encuentra ocupada");
        }

        if (!mascotaVacunada(cita.getMascota().getNumeroMascota())) {
            throw new MascotaNoVacunadaException("No tiene vacunas suministradas");
        }

        // Validar asistente disponible
        Asistente asistente = cita.getAsistente();
        if (asistente != null && !asistenteDisponible(asistente.getNombre(), asistente.getPaterno(), asistente.getMaterno())) {
            throw new AsistenteNoDisponibleException("El asistente ya tiene una cita en esa hora.");
        }

        // Validar veterinario disponible
        Veterinario veterinario = cita.getVeterinario();
        if (veterinario != null && !veterinarioDisponible(veterinario.getNombre(), veterinario.getPaterno(), veterinario.getMaterno())) {
            throw new VeterinarioNoDisponibleException("El veterinario ya tiene una cita en esa hora.");
        }

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cita);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // [Opcional] Método para listar citas ordenadas por criterio
    public List<Cita> listarCitasOrdenadasPor(String criterio) {
        EntityManager em = emf.createEntityManager();
        try {
            String campo;
            switch (criterio) {
                case "fecha": campo = "c.fechaHora"; break;
                case "nombre": campo = "c.cliente.nombre"; break;
                case "paterno": campo = "c.cliente.paterno"; break;
                case "materno": campo = "c.cliente.materno"; break;
                default: campo = "c.fechaHora";
            }
            String jpql = "SELECT c FROM Cita c ORDER BY " + campo;
            return em.createQuery(jpql, Cita.class).getResultList();
        } finally {
            em.close();
        }
    }
}
