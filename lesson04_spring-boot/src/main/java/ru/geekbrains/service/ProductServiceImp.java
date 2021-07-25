package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.controller.ProductListParams;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.ProductSpecifications;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<Product> findWithFilter(ProductListParams params) {
        Specification<Product> specification = Specification.where(null);

        if (params.getTitleFilter() != null && !params.getTitleFilter().isBlank()) {
            specification = specification.and(ProductSpecifications.titlePrefix(params.getTitleFilter()));
        }
        if (params.getMinCostFilter() != null) {
            specification = specification.and(ProductSpecifications.minCost(params.getMinCostFilter()));
        }
        if (params.getMaxCostFilter() != null) {
            specification = specification.and(ProductSpecifications.maxCost(params.getMaxCostFilter()));
        }

        String sortColumn;
        if (params.getSortBy() != null && !params.getSortBy().isBlank()) {
            sortColumn = params.getSortBy();
        } else {
            sortColumn = "id";
        }

        Sort sort;
        if ("desc".equals(params.getSortOrder())) {
            sort = Sort.by(sortColumn).descending();
        } else {
            sort = Sort.by(sortColumn).ascending();
        }

        return productRepository.findAll(specification,
                PageRequest.of(Optional.ofNullable(params.getPage()).orElse(1) - 1,
                        Optional.ofNullable(params.getSize()).orElse(3),sort));
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
