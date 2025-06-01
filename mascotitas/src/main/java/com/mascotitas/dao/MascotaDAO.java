package com.mascotitas.dao;

import com.mascotitas.model.Mascota;
import com.mascotitas.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
}
