package com.dairy.backend.product.controller;

import com.dairy.backend.product.model.Product;
import com.dairy.backend.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return service.createProduct(product);
    }

    @GetMapping
    public List<Product> listActive() {
        return service.getAllActiveProducts();
    }

    @PatchMapping("/{id}/disable")
    public void disable(@PathVariable UUID id) {
        service.disableProduct(id);
    }
}
