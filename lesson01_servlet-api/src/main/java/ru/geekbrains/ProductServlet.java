package ru.geekbrains;

import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/product")
public class ProductServlet extends HttpServlet {
    private ProductRepository repository;

    @Override
    public void init() throws ServletException {
        repository = (ProductRepository) getServletContext().getAttribute("productRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        writer.println("<table>");
        writer.println("<caption>Product list</caption>");
        writer.println("<tr>");
        writer.println("<th>ID</th>");
        writer.println("<th>Title</th>");
        writer.println("<th>Cost</th>");
        writer.println("</tr>");

        for (Product product : repository.findAll()) {
            writer.println("<tr>");
            writer.println("<td>" + product.getId() + "</td>");
            writer.println("<td>" + product.getTitle() + "</td>");
            writer.println("<td>" + product.getCost() + "</td>");
            writer.println("</tr>");
        }

        writer.println("</table>");
    }
}
