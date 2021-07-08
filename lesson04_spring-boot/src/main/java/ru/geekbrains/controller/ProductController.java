package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductRepository repository;

    @Autowired
    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String listPage(Model model,
                           @RequestParam(name = "minCostFilter", required = false) String minCostFilter,
                           @RequestParam(name = "maxCostFilter", required = false) String maxCostFilter) {
        logger.info("Product list page requested");

        List<Product> products;
        BigDecimal min = null;
        BigDecimal max = null;

        if (minCostFilter != null && !minCostFilter.isEmpty()) {
            min = new BigDecimal(minCostFilter);
        }

        if (maxCostFilter != null && !maxCostFilter.isEmpty()) {
            max = new BigDecimal(maxCostFilter);
        }

        if (min != null && max != null) {
            products = repository.findByCostBetween(min, max);
        } else if (min != null) {
            products = repository.findByCostGreaterThanEqual(min);
        } else if (max != null) {
            products = repository.findByCostLessThanEqual(max);
        } else {
            products = repository.findAll();
        }

        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        logger.info("Change product page requested");

        model.addAttribute("product", new Product());
        return "product_form";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        logger.info("Editing product");

        Product currentProduct = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        model.addAttribute("product", currentProduct);

        return "product_form";
    }

    @DeleteMapping("/{id}")
    public String removeProduct(@PathVariable("id") Long id, Model model) {
        logger.info("Deleting product");

        repository.deleteById(id);

        return "redirect:/product";
    }

    @PostMapping
    public String update(@Valid Product product, BindingResult result) {
        logger.info("Updating product");

        if (result.hasErrors()) {
            return "product_form";
        }

        repository.save(product);
        return "redirect:/product";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
