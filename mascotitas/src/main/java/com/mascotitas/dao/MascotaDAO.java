package com.mascotitas.dao;

import com.mascotitas.model.Mascota;
import com.mascotitas.util.HibernateUtil;

import jakarta.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.Collections;
import java.util.List;

public class MascotaDAO {
    public void guardar(Mascota mascota) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(mascota);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    // Método adaptado para listar todas las mascotas usando Hibernate puro
    public List<Mascota> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Mascota", Mascota.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return java.util.Collections.emptyList();
        }
    }
    public void actualizar(Mascota mascota) {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(mascota);  // Actualiza la mascota
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    public List<Mascota> buscarPorClienteId(int idCliente) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Mascota> mascotas = session
            .createQuery("FROM Mascota m WHERE m.cliente.id = :id", Mascota.class)
            .setParameter("id", idCliente)
            .list();
        session.close();
        return mascotas;
    }

}
