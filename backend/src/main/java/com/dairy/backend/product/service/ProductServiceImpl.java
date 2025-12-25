package com.dairy.backend.product.service;

import com.dairy.backend.product.model.Product;
import com.dairy.backend.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product createProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public List<Product> getAllActiveProducts() {
        return repository.findByActiveTrue();
    }

    @Override
    public void disableProduct(UUID productId) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setActive(false);
        repository.save(product);
    }
}
