package com.mascotitas.dao;

import com.mascotitas.model.Gerente;
import com.mascotitas.util.HibernateUtil;
import org.hibernate.Session;

import java.util.Collections;
import java.util.List;

public class GerenteDAO {
    public void guardar(Gerente gerente) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(gerente);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Gerente> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Gerente", Gerente.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
