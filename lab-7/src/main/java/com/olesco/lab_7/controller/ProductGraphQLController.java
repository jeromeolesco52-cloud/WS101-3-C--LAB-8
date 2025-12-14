package com.olesco.lab_7.controller;

import com.olesco.lab_7.model.Product;
import com.olesco.lab_7.service.ProductService;
import com.olesco.lab_7.model.ProductInput;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class ProductGraphQLController {

    private final ProductService productService;

    public ProductGraphQLController(ProductService productService) {
        this.productService = productService;
    }

    // --- QUERY MAPPINGS ---

    // Maps to: query { products }
    @QueryMapping
    public List<Product> products() {
        return productService.findAll();
    }

    // Maps to: query { product(id: 1) }
    @QueryMapping
    public Product product(@Argument Long id) {
        // The Optional maps cleanly to a nullable GraphQL field
        return productService.findById(id).orElse(null);
    }

    // --- MUTATION MAPPINGS ---

    // Maps to: mutation { saveProduct(product: { ... }) }
    @MutationMapping
    public Product saveProduct(@Argument ProductInput product) {
        // ... logic for create/update ...
        if (product.id() == null) {
            Product newProduct = new Product(null, product.name(), product.price());
            return productService.save(newProduct);
        }

        Product updateDetails = new Product(product.id(), product.name(), product.price());
        return productService.update(product.id(), updateDetails).orElseThrow(
                () -> new RuntimeException("Product not found for update with ID: " + product.id())
        );
    }

    // Maps to: mutation { deleteProduct(id: 4) }
    @MutationMapping
    public boolean deleteProduct(@Argument Long id) {
        return productService.delete(id);
    }
}