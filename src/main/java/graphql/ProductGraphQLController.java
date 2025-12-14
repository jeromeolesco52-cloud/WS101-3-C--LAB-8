package graphql;

import com.Etac.John.Llyod.WS101_Act7.model.Product;
import com.Etac.John.Llyod.WS101_Act7.service.ProductService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductGraphQLController {

    private final ProductService productService;

    public ProductGraphQLController(ProductService productService) {
        this.productService = productService;
    }

    @QueryMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @QueryMapping
    public Optional<Product> getProductById(@Argument Long id) {
        return productService.getProductById(id);
    }

    @QueryMapping
    public List<Product> searchProducts(@Argument String name) {
        return productService.searchProductsByName(name);
    }

    @QueryMapping
    public Integer getProductsCount() {
        return productService.getAllProducts().size();
    }

    @MutationMapping
    public Product createProduct(@Argument String name, @Argument Double price) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        return productService.createProduct(product);
    }

    @MutationMapping
    public Optional<Product> updateProduct(@Argument Long id, @Argument String name, @Argument Double price) {
        Product productDetails = new Product();
        productDetails.setName(name);
        productDetails.setPrice(price);
        return productService.updateProduct(id, productDetails);
    }

    @MutationMapping
    public Boolean deleteProduct(@Argument Long id) {
        return productService.deleteProduct(id);
    }

    @MutationMapping
    public Product addProduct(@Argument ProductInput productInput) {
        Product product = new Product();
        product.setName(productInput.name());
        product.setPrice(productInput.price());
        return productService.createProduct(product);
    }

    public record ProductInput(String name, Double price) {}
}