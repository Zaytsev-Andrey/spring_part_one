package ru.geekbrains.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.controller.NotFoundException;
import ru.geekbrains.persist.Product;
import ru.geekbrains.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductResource {

    private final ProductService productService;

    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public Product findById(@PathVariable("id") Long id) {
        return productService.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @PostMapping(produces = "application/json")
    public Product create(@RequestBody Product product) {
        if (product.getId() != null) {
            throw new BadRequestException("Product Id should be null");
        }

        productService.save(product);
        return product;
    }

    @PutMapping(produces = "application/json")
    public void update(@RequestBody Product product) {
        if (product.getId() == null) {
            throw new BadRequestException("Product Id shouldn't be null");
        }

        productService.save(product);
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @ExceptionHandler
    public ResponseEntity<ProductErrorResponse> handleNotFoundException(NotFoundException e) {
        return handleException(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ProductErrorResponse> handleBadRequestException(BadRequestException e) {
        return handleException(e, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ProductErrorResponse> handleException(Exception e, HttpStatus status) {
        ProductErrorResponse productErrorResponse = new ProductErrorResponse();
        productErrorResponse.setStatus(status.value());
        productErrorResponse.setMessage(e.getMessage());
        productErrorResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(productErrorResponse, status);
    }
}
