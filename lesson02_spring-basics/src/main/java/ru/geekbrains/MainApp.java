package ru.geekbrains;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.geekbrains.persist.Cart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainApp {
    private static Cart cart;

    public static void main(String[] args) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        cart = context.getBean("cart", Cart.class);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String commandString = "";

        while (!"exit".equals(commandString)) {
            cart.showProduct();
            showCommand();
            commandString = reader.readLine()
                    .toLowerCase()
                    .trim();
            if (commandString.startsWith("add")) {
                add(commandString);
            } else if (commandString.startsWith("del")) {
                del(commandString);
            } else if ("new".equals(commandString)) {
                cart = context.getBean("cart", Cart.class);
            }
        }
    }

    private static void showCommand() {
        System.out.println();
        System.out.println("Enter: add <product_id> or del <product_id> or exit");
        System.out.print("> ");
    }

    private static void del(String commandString) {
        try {
            long id = Long.parseLong(commandString.split(" ")[1]);
            cart.removeProduct(id);
        } catch (Exception e) {
            System.out.println("Command not found");
        }
    }

    private static void add(String commandString) {
        try {
            long id = Long.parseLong(commandString.split(" ")[1]);
            cart.addProduct(id);
        } catch (Exception e) {
            System.out.println("Command not found");
        }
    }
}
