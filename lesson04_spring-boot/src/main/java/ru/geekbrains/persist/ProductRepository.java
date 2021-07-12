package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findByCostBetween(BigDecimal min, BigDecimal max);

    List<Product> findByCostGreaterThanEqual(BigDecimal min);

    List<Product> findByCostLessThanEqual(BigDecimal max);
}
