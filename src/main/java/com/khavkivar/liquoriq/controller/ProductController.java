package com.khavkivar.liquoriq.controller;

import com.khavkivar.liquoriq.model.Product;
import com.khavkivar.liquoriq.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @GetMapping("{id}")
    public Product getProductById(@PathVariable long id) {
        return productService.getProduct(id);
    }
}
