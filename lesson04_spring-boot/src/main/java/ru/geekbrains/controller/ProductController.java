package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.ProductSpecifications;

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
                           @RequestParam("titleFilter") Optional<String> titleFilter,
                           @RequestParam("minCostFilter") Optional<BigDecimal> minCostFilter,
                           @RequestParam("maxCostFilter") Optional<BigDecimal> maxCostFilter,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam("sortBy") Optional<String> sortBy) {
        logger.info("Product list page requested");

        Specification<Product> specification = Specification.where(null);
        if (titleFilter.isPresent() && !titleFilter.get().isBlank()) {
            specification = specification.and(ProductSpecifications.titlePrefix(titleFilter.get()));
        }
        if (minCostFilter.isPresent()) {
            specification = specification.and(ProductSpecifications.minCost(minCostFilter.get()));
        }
        if (maxCostFilter.isPresent()) {
            specification = specification.and(ProductSpecifications.maxCost(maxCostFilter.get()));
        }

        String sortColumn;
        if (sortBy.isPresent() && !sortBy.get().isBlank()) {
            sortColumn = sortBy.get();
        } else {
            sortColumn = "id";
        }

        model.addAttribute("products", repository.findAll(specification,
                PageRequest.of(page.orElse(1) - 1, size.orElse(3),
                        Sort.by(sortColumn))));
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
