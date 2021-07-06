package ru.geekbrains;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.geekbrains.dao.CustomerDAO;
import ru.geekbrains.dao.OrderService;
import ru.geekbrains.dao.ProductDAO;

@Configuration
public class AppConfig {

    @Bean(name = "sessionManager")
    public SessionManager sessionManager() {
        return new SessionManager();
    }

    @Bean
    public CustomerDAO customerDAO() {
        return new CustomerDAO(sessionManager());
    }

    @Bean
    public ProductDAO productDAO() {
        return new ProductDAO(sessionManager());
    }

    @Bean
    public OrderService orderService() {
        return new OrderService(sessionManager());
    }
}
