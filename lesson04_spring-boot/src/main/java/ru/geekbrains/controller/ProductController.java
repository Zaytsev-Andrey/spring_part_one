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
import ru.geekbrains.service.ProductService;

import javax.validation.Valid;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public String listPage(Model model, ProductListParams productListParams) {
        logger.info("Product list page requested");



        model.addAttribute("products", service.findWithFilter(productListParams));
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

        Product currentProduct = service.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        model.addAttribute("product", currentProduct);

        return "product_form";
    }

    @DeleteMapping("/{id}")
    public String removeProduct(@PathVariable("id") Long id, Model model) {
        logger.info("Deleting product");

        service.deleteById(id);

        return "redirect:/product";
    }

    @PostMapping
    public String update(@Valid Product product, BindingResult result) {
        logger.info("Updating product");

        if (result.hasErrors()) {
            return "product_form";
        }

        service.save(product);
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
