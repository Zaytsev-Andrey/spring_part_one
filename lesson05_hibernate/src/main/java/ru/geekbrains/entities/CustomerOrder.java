package ru.geekbrains.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "customer_order")
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int count;

    @Column(name = "date_of_buy", nullable = false)
    private Timestamp dateOfBuy;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public CustomerOrder() {
    }

    public CustomerOrder(Customer customer, Product product, int count) {
        this.customer = customer;
        this.product = product;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Timestamp getDateOfBuy() {
        return dateOfBuy;
    }

    public void setDateOfBuy(Timestamp dateOfBuy) {
        this.dateOfBuy = dateOfBuy;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return String.format("CustomerOrder{id=%d, Customer=%s, Product=%s, Count=%d, dateOfByy=%s}",
                id, customer.getName(), product.getTitle(), count, dateOfBuy);
    }
}
