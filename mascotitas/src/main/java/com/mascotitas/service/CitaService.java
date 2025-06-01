package com.mascotitas.service;

import com.mascotitas.interfaces.RevisionDeCitas;
import com.mascotitas.model.*;
import com.mascotitas.exception.CitaOcupadaException;
import com.mascotitas.exception.MascotaNoVacunadaException;

import jakarta.persistence.*;
import java.util.*;

/*
 * Clase: CitaService
 * Implementa RevisionDeCitas y maneja lógica de agendamiento de citas
 */
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

    public void agendarCita(Cita cita) throws CitaOcupadaException, MascotaNoVacunadaException {
        if (!revisarDisponibilidad(cita.getFechaHora())) {
            throw new CitaOcupadaException("No puede agendar la cita, ya se encuentra ocupada");
        }

        if (!mascotaVacunada(cita.getMascota().getNumeroMascota())) {
            throw new MascotaNoVacunadaException("No tiene vacunas suministradas");
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
}
