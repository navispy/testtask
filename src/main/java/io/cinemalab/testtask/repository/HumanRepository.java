/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.cinemalab.testtask.repository;

import io.cinemalab.testtask.models.Human;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.hibernate.Session;

/**
 *
 * @author knud
 */
public class HumanRepository {

    private EntityManager entityManager;

    public HumanRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Human> save(Human human) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(human);
            entityManager.getTransaction().commit();
            return Optional.of(human);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Human> update(Human human) {
        try {
            entityManager.getTransaction().begin();

            Session session = entityManager.unwrap(Session.class);
            session.update(human);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public Optional<Human> delete(Human human) {
        try {
            entityManager.getTransaction().begin();

            Session session = entityManager.unwrap(Session.class);
            session.delete(human);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
