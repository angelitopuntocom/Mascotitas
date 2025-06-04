package com.mascotitas.dao;

import com.mascotitas.model.Veterinario;
import com.mascotitas.util.HibernateUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.hibernate.Session;
import java.util.List;
import java.util.Collections;

public class VeterinarioDAO {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("mascotitasPU");
    
    public List<Veterinario> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Veterinario", Veterinario.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    public void guardar(Veterinario veterinario) {
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        session.beginTransaction();
        session.persist(veterinario);
        session.getTransaction().commit();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
    public void eliminar(Veterinario veterinario) {
    EntityManager em = emf.createEntityManager();
    try {
        em.getTransaction().begin();
        Veterinario vetRef = em.find(Veterinario.class, veterinario.getIdVeterinario());
        if (vetRef != null) {
            em.remove(vetRef);
        }
        em.getTransaction().commit();
    } finally {
        em.close();
    }
    }

}
