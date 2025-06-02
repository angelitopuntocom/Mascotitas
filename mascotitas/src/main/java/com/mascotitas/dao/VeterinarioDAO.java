package com.mascotitas.dao;

import com.mascotitas.model.Veterinario;
import com.mascotitas.util.HibernateUtil;
import org.hibernate.Session;
import java.util.List;
import java.util.Collections;

public class VeterinarioDAO {
    public List<Veterinario> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Veterinario", Veterinario.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
