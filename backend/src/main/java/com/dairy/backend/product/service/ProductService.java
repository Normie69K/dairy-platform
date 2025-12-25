package com.dairy.backend.product.service;

import com.dairy.backend.product.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    Product createProduct(Product product);

    List<Product> getAllActiveProducts();

    void disableProduct(UUID productId);

}
