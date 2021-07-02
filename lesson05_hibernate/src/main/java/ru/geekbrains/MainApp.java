package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.entities.Product;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class MainApp {

    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        ProductDAO dao = new ProductDAO(emFactory.createEntityManager());

        System.out.printf("%nCreating or Updating products%n");
        fillProducts(dao);

        System.out.printf("%nAll products%n");
        dao.findAll().forEach(System.out::println);

        Long productId = 15L;
        System.out.printf("%nProducts with id=%d%n", productId);
        System.out.println(dao.findById(productId));


        System.out.printf("%nCreating or Updating product with id=%d%n", productId);
        Product currentProduct = dao.saveOrUpdate(new Product(productId, "Pineapple", 1));


        System.out.printf("%nCreated or Updated product with id=%d%n", productId);
        System.out.printf("Current value product: %s%n", currentProduct);

        productId = 2L;
        System.out.printf("%nDelete product with id=%d%n", productId);
        dao.deleteById(productId);

        System.out.printf("%nAll products%n");
        dao.findAll().forEach(System.out::println);

        dao.close();
    }

    private static void fillProducts(ProductDAO dao) {
        List.of(
            new Product("Apple", 2),
            new Product("Apricot", 1),
            new Product("Avocado", 5),
            new Product("Pineapple", 2),
            new Product("Grapefruit", 7),
            new Product("Mango", 4),
            new Product("Orange", 3),
            new Product("Plum", 5),
            new Product("Tangerine", 1),
            new Product("Pineapple", 6)
        ).forEach(dao::saveOrUpdate);
    }
}
