package com.Etac.John.Llyod.WS101_Act7.service;

import com.Etac.John.Llyod.WS101_Act7.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {
    private final List<Product> products = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(4);

    public ProductService() {
        products.add(new Product(1L, "Laptop Pro", 1299.99));
        products.add(new Product(2L, "Wireless Mouse", 29.99));
        products.add(new Product(3L, "Mechanical Keyboard", 89.99));
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public Optional<Product> getProductById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    public Product createProduct(Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (product.getPrice() == null || product.getPrice() < 0) {
            throw new IllegalArgumentException("Product price must be non-negative");
        }

        Long newId = idCounter.getAndIncrement();
        Product newProduct = new Product(newId, product.getName(), product.getPrice());
        products.add(newProduct);
        return newProduct;
    }

    public Optional<Product> updateProduct(Long id, Product productDetails) {
        Optional<Product> existingProduct = getProductById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();

            if (productDetails.getName() != null && !productDetails.getName().trim().isEmpty()) {
                product.setName(productDetails.getName());
            }
            if (productDetails.getPrice() != null && productDetails.getPrice() >= 0) {
                product.setPrice(productDetails.getPrice());
            }
            return Optional.of(product);
        }
        return Optional.empty();
    }

    public boolean deleteProduct(Long id) {
        return products.removeIf(product -> product.getId().equals(id));
    }

    public List<Product> searchProductsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return getAllProducts();
        }
        return products.stream()
                .filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }
}
