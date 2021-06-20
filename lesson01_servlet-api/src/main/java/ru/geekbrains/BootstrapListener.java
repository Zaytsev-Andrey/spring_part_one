package ru.geekbrains;

import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.math.BigDecimal;

@WebListener
public class BootstrapListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();

        ProductRepository repository = new ProductRepository();
        repository.save(new Product("Apple", new BigDecimal("0.50")));
        repository.save(new Product("Apricot", new BigDecimal("0.70")));
        repository.save(new Product("Avocado", new BigDecimal("1.20")));
        repository.save(new Product("Pineapple", new BigDecimal("1.50")));
        repository.save(new Product("Grapefruit", new BigDecimal("1.00")));
        repository.save(new Product("Mango", new BigDecimal("1.60")));
        repository.save(new Product("Orange", new BigDecimal("0.80")));
        repository.save(new Product("Plum", new BigDecimal("0.40")));
        repository.save(new Product("Tangerine", new BigDecimal("1.40")));
        repository.save(new Product("Pineapple", new BigDecimal("2.30")));

        sc.setAttribute("productRepository", repository);
    }
}
