package ru.geekbrains.persist;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepository {

    private Map<Long, Product> products = new ConcurrentHashMap<>();

    private AtomicLong identity = new AtomicLong(0);

    @PostConstruct
    public void init() {
        this.update(new Product("Apple", "0.50"));
        this.update(new Product("Pineapple", "1.50"));
        this.update(new Product("Grapefruit", "1.00"));
        this.update(new Product("Mango", "1.60"));
        this.update(new Product("Tangerine", "1.40"));
    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public Product findByID(long id) {
        return products.get(id);
    }

    public void update(Product product) {
        if (product.getId() == null) {
            product.setId(identity.incrementAndGet());
        }
        products.put(product.getId(), product);
    }

    public void delete(long id) {
        products.remove(id);
    }
}
