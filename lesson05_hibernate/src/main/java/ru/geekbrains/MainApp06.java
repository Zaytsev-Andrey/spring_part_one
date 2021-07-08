package ru.geekbrains;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.geekbrains.dao.OrderService;
import ru.geekbrains.entities.CustomerOrder;
import ru.geekbrains.entities.Price;

import java.math.BigDecimal;

public class MainApp06 {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        OrderService orderService = context.getBean("orderService", OrderService.class);

        System.out.println("=== Product list by customer ID ===");
        orderService.productListByCustomerId(1L).forEach(
                os -> System.out.printf("Product=%s\tDate=%s\tCount=%d%n",
                        os.getProduct().getTitle(), os.getDateOfBuy(), os.getCount())
        );
        System.out.println();

        System.out.println("Customer list of product");
        orderService.customerListOfProduct(2L).forEach(
                c -> System.out.printf("Customer=%s%n", c.getName())
        );
        System.out.println();

        System.out.println("=== Cost of bought product ===");
        orderService.costOfBoughtProduct(1L, 1L).forEach(o -> {
                    CustomerOrder order = (CustomerOrder) o[0];
                    Price price = (Price) o[1];
                    BigDecimal cost = new BigDecimal(order.getCount()).multiply(price.getCost());
                    System.out.printf("Date=%s\t\tCustomer=%s\t\tProduct=%s\t\tPrice=%.2f\t\tCount=%d\t\tCost=%.2f%n",
                            order.getDateOfBuy(), order.getCustomer().getName(), order.getProduct().getTitle(),
                            price.getCost(), order.getCount(), cost);
                }
        );
        System.out.println();

    }

}
