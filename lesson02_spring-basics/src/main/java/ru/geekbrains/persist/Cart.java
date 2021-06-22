package ru.geekbrains.persist;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private ProductRepository productRepository;
    private Map<Product, Integer> products;

    public Cart(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.products = new HashMap<>();
    }

    public void addProduct(long id) {
        Product product = productRepository.findByID(id);
        if (products.containsKey(product)) {
            products.computeIfPresent(product, (k, v) -> ++v);
        } else {
            products.put(product, 1);
        }
    }

    public void removeProduct(long id) {
        products.remove(productRepository.findByID(id));
    }

    public void showProduct() {
        printProductList();
        printCart();

    }

    private void printCart() {
        System.out.println("---------------------------------------------------");
        System.out.println("Cart");
        System.out.println("---------------------------------------------------");
        System.out.println("ID\tProduct\t\t\tCount\t\tCost");
        for (var entry : products.entrySet()) {
            Product p = entry.getKey();
            Integer count = entry.getValue();
            BigDecimal price = p.getCost().multiply(new BigDecimal(count));
            System.out.printf("%s\t%s\t\t\t%s\t\t%s%n", p.getId(), p.getTitle(), count, price);
        }
    }

    private void printProductList() {
        System.out.println("===================================================");
        System.out.println("Product list");
        System.out.println("===================================================");
        System.out.println("ID\tProduct\t\t\tCost");
        for (Product p : productRepository.findAll()) {
            System.out.printf("%s\t%s\t\t\t%s%n", p.getId(), p.getTitle(), p.getCost());
        }
    }
}
