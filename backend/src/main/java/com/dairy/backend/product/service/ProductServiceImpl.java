package com.dairy.backend.product.service;

import com.dairy.backend.product.model.Product;
import com.dairy.backend.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product create(Product product) {
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        return repository.save(product);
    }

    @Override
    public Product update(UUID id, Product updated) {
        Product existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        existing.setName(updated.getName());
        existing.setPrice(updated.getPrice());
        existing.setUnit(updated.getUnit());
        existing.setActive(updated.isActive());

        return repository.save(existing);
    }

    @Override
    public Product getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    @Override
    public List<Product> getActiveProducts() {
        return repository.findByActiveTrue();
    }

    @Override
    public void deactivate(UUID id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        product.setActive(false);
        repository.save(product);
    }
}
