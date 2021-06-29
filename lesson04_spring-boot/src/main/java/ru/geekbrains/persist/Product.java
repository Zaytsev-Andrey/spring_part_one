package ru.geekbrains.persist;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class Product {

    private Long id;

    @NotBlank
    private String title;

    @Positive
    private BigDecimal cost;

    public Product() {
    }

    public Product(String title, String cost) {
        this.title = title;
        this.cost = new BigDecimal(cost);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
