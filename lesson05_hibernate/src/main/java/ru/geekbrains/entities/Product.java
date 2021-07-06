package ru.geekbrains.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false)
    private String title;

    @OneToMany(mappedBy = "product")
    private List<Price> prices = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<CustomerOrder> orders = new ArrayList<>();

    public Product() {
    }

    public Product(String title) {
        this.title = title;
    }

    public Product(Long id, String title) {
        this.id = id;
        this.title = title;
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

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public List<CustomerOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<CustomerOrder> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return String.format("Product{id=%d, title=%s, price=%d}", id, title);
    }
}
