package ru.geekbrains;

import ru.geekbrains.entities.Product;

import javax.persistence.EntityManager;
import java.util.List;

public class ProductDAO {
    private EntityManager em;

    public ProductDAO(EntityManager emFactory) {
        this.em = emFactory;
    }

    public Product saveOrUpdate(Product product) {
        em.getTransaction().begin();
        Product changedProduct = em.merge(product);
        em.getTransaction().commit();

        return changedProduct;
    }

    public Product findById(Long id) {
        em.getTransaction().begin();
        Product product = em.find(Product.class, id);
        em.getTransaction().commit();

        return product;
    }

    public List<Product> findAll() {
        em.getTransaction().begin();
        List<Product> products = em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
        em.getTransaction().commit();

        return products;
    }

    public void deleteById(Long id) {
        em.getTransaction().begin();
        Product product = em.getReference(Product.class, id);
        em.remove(product);
        em.getTransaction().commit();
    }

    public void close() {
        em.close();
    }
}
