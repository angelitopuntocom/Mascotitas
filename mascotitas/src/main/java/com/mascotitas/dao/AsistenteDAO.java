package com.mascotitas.dao;

import com.mascotitas.model.Asistente;
import com.mascotitas.util.HibernateUtil;
import org.hibernate.Session;
import java.util.List;
import java.util.Collections;

public class AsistenteDAO {
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

}
