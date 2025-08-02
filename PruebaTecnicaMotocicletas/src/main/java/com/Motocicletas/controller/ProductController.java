package com.Motocicletas.controller;

import com.Motocicletas.model.Product;
import com.Motocicletas.model.Sale;
import com.Motocicletas.service.IProductService;
import com.Motocicletas.service.implementations.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/products/{id}")
    public Product getProductById (@PathVariable Long id) {
        return productService.findById(id);
    }

    @GetMapping("/products")
    public List<Product> getProducts () {
        return productService.findAll();
    }

    @PostMapping("/products")
    public ResponseEntity<Product> saveProduct (@RequestBody Product product) {
        Product productSaved = productService.createProduct(product);
        return new ResponseEntity<>(productSaved, HttpStatus.CREATED);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProductById(@PathVariable Long id){
        productService.deleteById(id);
    }
}
