package ru.geekbrains.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "price")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal cost;

    @Column(name = "date_of_set", nullable = false)
    private Timestamp dateOfSet;

    public Price() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Timestamp getDateOfSet() {
        return dateOfSet;
    }

    public void setDateOfSet(Timestamp dateOfSet) {
        this.dateOfSet = dateOfSet;
    }

    @Override
    public String toString() {
        return String.format("Price{Id=%d, Product=%s, Cost=%f, DateOfSet=%s}",
                id, product.getTitle(), cost, dateOfSet);
    }
}
