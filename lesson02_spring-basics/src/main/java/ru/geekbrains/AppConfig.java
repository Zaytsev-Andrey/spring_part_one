package ru.geekbrains;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.geekbrains.persist.Cart;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;


@Configuration
public class AppConfig {

    @Bean(name = "productRepository")
    public ProductRepository productRepository() {
        ProductRepository repository = new ProductRepository();
        repository.save(new Product("Apple", "0.50"));
        repository.save(new Product("Pineapple", "1.50"));
        repository.save(new Product("Grapefruit", "1.00"));
        repository.save(new Product("Mango", "1.60"));
        repository.save(new Product("Tangerine", "1.40"));
        return repository;
    }

    @Bean(name = "cart")
    @Scope("prototype")
    public Cart cart() {
        return new Cart(productRepository());
    }
}
