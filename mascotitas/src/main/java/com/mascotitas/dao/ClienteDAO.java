package com.mascotitas.dao;

import com.mascotitas.model.Cliente;
import com.mascotitas.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ClienteDAO {
    public void guardar(Cliente cliente) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(cliente);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}
