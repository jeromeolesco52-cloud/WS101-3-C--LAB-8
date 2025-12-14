package com.olesco.lab_7.service;

import com.olesco.lab_7.model.Product;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service // Registers this class as a Spring Bean
public class ProductService {
    private final List<Product> products = new ArrayList<>();

    // Initialize with mock data [cite: 244]
    public ProductService() {
        products.add(new Product(1L, "Laptop Pro", 1200.00));
        products.add(new Product(2L, "Smartphone", 800.00));
        products.add(new Product(3L, "Headphones", 150.00));
    }

    // Method to return all products
    public List<Product> findAll() {
        return products;
    }

    // Method to find a product by ID
    public Optional<Product> findById(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    // Method to save a new product (Simulate auto-increment ID) [cite: 272]
    public Product save(Product product) {
        long newId = products.isEmpty() ? 1 : products.get(products.size() - 1).getId() + 1;
        product.setId(newId);
        products.add(product);
        return product;
    }

    // Method to delete a product
    public boolean delete(Long id) {
        return products.removeIf(p -> p.getId().equals(id));
    }

    // Method to update a product
    public Optional<Product> update(Long id, Product newDetails) {
        return findById(id).map(existingProduct -> {
            existingProduct.setName(newDetails.getName());
            existingProduct.setPrice(newDetails.getPrice());
            return existingProduct;
        });
    }
}