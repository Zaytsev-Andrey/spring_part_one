package ru.geekbrains.service;

import org.springframework.data.domain.Page;
import ru.geekbrains.controller.ProductListParams;
import ru.geekbrains.persist.Product;

import java.util.List;
import java.util.Optional;


public interface ProductService {

    Page<Product> findWithFilter(ProductListParams productListParams);

    Optional<Product> findById(Long id);

    void deleteById(Long id);

    void save(Product product);

    List<Product> findAll();
}
