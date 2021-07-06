package ru.geekbrains.dao;

import ru.geekbrains.SessionManager;
import ru.geekbrains.entities.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class ProductDAO {
    private SessionManager manager;

    public ProductDAO(SessionManager manager) {
        this.manager = manager;
    }

    public void save(Product product) {
        if (product.getId() == null) {
            insert(product);
        } else {
            update(product);
        }
    }

    public void insert(Product product) {
        manager.executeInTransaction(em -> em.persist(product));
    }

    public void update(Product product) {
        manager.executeInTransaction(em -> em.merge(product));
    }

    public Optional<Product> findById(Long id) {
        return manager.executeForEntityManager(em -> Optional.ofNullable(em.find(Product.class, id)));
    }

    public List<Product> findAll() {
        return manager.executeForEntityManager(
                em -> em.createQuery("SELECT p FROM Product p", Product.class).getResultList()
        );
    }

    public void deleteById(Long id) {
        manager.executeInTransaction(
                em -> em.createQuery("DELETE FROM Product p WHERE p.id = :id")
                    .setParameter("id", id)
                    .executeUpdate()
        );
    }
}
