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

@WebServlet(urlPatterns = "/product/*")
public class ProductServlet extends HttpServlet {
    private ProductRepository repository;

    @Override
    public void init() throws ServletException {
        repository = (ProductRepository) getServletContext().getAttribute("productRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        if (req.getPathInfo() == null) {
            writer.println("<table>");
            writer.println("<caption>Product list</caption>");
            writer.println("<tr>");
            writer.println("<th>ID</th>");
            writer.println("<th>Title</th>");
            writer.println("<th>Cost</th>");
            writer.println("</tr>");

            for (Product product : repository.findAll()) {
                String link = req.getContextPath() + req.getServletPath() + "/" + product.getId();
                writer.println("<tr>");
                writer.println("<td>" + product.getId() + "</td>");
                writer.printf("<td><a href=\"%s\">%s</a></td>", link, product.getTitle());
                writer.println("<td>" + product.getCost() + "</td>");
                writer.println("</tr>");
            }

            writer.println("</table>");
        } else {
            String pathInfo = req.getPathInfo().substring(1);
            long id;
            try {
                id = Long.parseLong(pathInfo);
            } catch (NumberFormatException e) {
                writer.println("<b>Product not found</b>");
                return;
            }

            Product product;
            if ((product = repository.findByID(id)) != null) {
                writer.printf("<b>Product:</b> %s<br>", product.getTitle());
                writer.printf("<b>Cost:</b> %s $<br>", product.getCost());
            } else {
                writer.println("<b>Product not found</b>");
            }
        }
    }
}
