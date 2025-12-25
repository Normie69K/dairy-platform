package com.dairy.backend.product.service;

import com.dairy.backend.product.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    Product create(Product product);

    Product update(UUID id, Product updated);

    Product getById(UUID id);

    List<Product> getActiveProducts();

    void deactivate(UUID id);
}
