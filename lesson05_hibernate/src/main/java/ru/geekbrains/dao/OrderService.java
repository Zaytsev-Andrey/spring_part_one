package ru.geekbrains.dao;

import ru.geekbrains.SessionManager;
import ru.geekbrains.entities.Customer;
import ru.geekbrains.entities.CustomerOrder;

import java.util.List;


public class OrderService {

    private SessionManager manager;

    public OrderService(SessionManager manager) {
        this.manager = manager;
    }

    public List<CustomerOrder> productListByCustomerId(Long id) {
        return manager.executeForEntityManager(
                em -> em.createQuery("select co from CustomerOrder co where co.customer.id = :id", CustomerOrder.class)
                        .setParameter("id", id)
                        .getResultList()
        );
    }

    public List<Customer> customerListOfProduct(Long id) {
        return manager.executeForEntityManager(
                em -> em.createQuery("select distinct co.customer from CustomerOrder co where co.product.id = :id", Customer.class)
                        .setParameter("id", id)
                        .getResultList()
        );
    }

    public List<Object[]> costOfBoughtProduct(Long customerId, Long productId) {
        return manager.executeForEntityManager(
                em -> em.createQuery("select co, p from CustomerOrder co " +
                        "join Price p on co.product = p.product and p.dateOfSet = (select max(pc.dateOfSet) " +
                            "from Price pc where pc.dateOfSet <= co.dateOfBuy " +
                            "group by pc.product having pc.product = co.product) " +
                        "where co.customer.id = :customerId and co.product.id = :productId")
                        .setParameter("customerId", customerId)
                        .setParameter("productId", productId)
                        .getResultList()
        );

    }
}
