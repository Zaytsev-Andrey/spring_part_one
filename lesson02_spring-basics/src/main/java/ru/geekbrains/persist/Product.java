package ru.geekbrains.persist;

import java.math.BigDecimal;

public class Product {
    private Long id;
    private String title;
    private BigDecimal cost;

    public Product(String title, String cost) {
        this.id = id;
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

    @Override
    public String toString() {
        return "ID: " + id + System.lineSeparator()
                + "Title: " + title + System.lineSeparator()
                + "Cost: " + cost;
    }
}
