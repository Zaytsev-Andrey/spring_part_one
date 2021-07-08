package ru.geekbrains.dao;

import ru.geekbrains.SessionManager;
import ru.geekbrains.entities.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class CustomerDAO {
    private SessionManager manager;

    public CustomerDAO(SessionManager manager) {
        this.manager = manager;
    }

    public void insert(Customer customer) {
        manager.executeInTransaction(em -> em.persist(customer));
    }

    public void update(Customer customer) {
        manager.executeInTransaction(em -> em.merge(customer));
    }

    public void save(Customer customer) {
        if (customer.getId() == null) {
            insert(customer);
        } else {
            update(customer);
        }
    }

    public void deleteById(Long id) {
        manager.executeInTransaction(
                em -> em.createQuery("DELETE FROM Customer c WHERE c.id = :id")
                .setParameter("id", id)
                .executeUpdate()
        );
    }

    public Optional<Customer> findById(Long id) {
        return manager.executeForEntityManager(
                em -> Optional.ofNullable(em.find(Customer.class, id))
        );
    }

    public List<Customer> findAll() {
        return manager.executeForEntityManager(
                em -> em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList()
        );
    }
}
