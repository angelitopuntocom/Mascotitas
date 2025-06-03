package com.mascotitas.dao;

import com.mascotitas.model.Cita;
import com.mascotitas.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CitaDAO {

    public void guardar(Cita cita) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(cita);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Cita> listarTodas() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Cita", Cita.class).list();
        }
    }

    public List<Cita> buscarPorVeterinario(int veterinarioId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Cita WHERE veterinario.id = :vid", Cita.class)
                          .setParameter("vid", veterinarioId)
                          .list();
        }
    }

    public List<Cita> buscarPorAsistente(int asistenteId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Cita WHERE asistente.id = :aid", Cita.class)
                          .setParameter("aid", asistenteId)
                          .list();
        }
    }
}
