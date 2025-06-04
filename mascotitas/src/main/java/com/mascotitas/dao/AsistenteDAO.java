package com.mascotitas.dao;

import com.mascotitas.model.Asistente;
import com.mascotitas.util.HibernateUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.hibernate.Session;
import java.util.List;
import java.util.Collections;

public class AsistenteDAO {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("mascotitasPU");
    
    public List<Asistente> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Asistente", Asistente.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    public void guardar(Asistente asistente) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(asistente);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void eliminar(Asistente asistente) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Asistente asistRef = em.find(Asistente.class, asistente.getIdAsistente());
            if (asistRef != null) {
                em.remove(asistRef);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

}
