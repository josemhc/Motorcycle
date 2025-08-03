package com.Motocicletas.controller;

import com.Motocicletas.dto.product.ProductDTO;
import com.Motocicletas.model.Product;
import com.Motocicletas.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById (@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);

        if(productOptional.isPresent()) {
            Product product = productOptional.get();

            ProductDTO productDTO = ProductDTO.builder()
                    .id(product.getId())
                    .productCode(product.getProductCode())
                    .item(product.getItem())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .brand(product.getBrand())
                    .build();

            return ResponseEntity.ok(productDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/products")
    public ResponseEntity<?> getProducts () {

        List<ProductDTO> productsDTOS = productService.findAll()
                .stream()
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .productCode(product.getProductCode())
                        .item(product.getItem())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .brand(product.getBrand())
                        .build()).toList();

        return ResponseEntity.ok(productsDTOS);
    }

    @PostMapping("/products")
    public ResponseEntity<?> saveProduct (@RequestBody ProductDTO productDTO) throws URISyntaxException {
        productService.createProduct(Product.builder()
                .id(productDTO.getId())
                .productCode(productDTO.getProductCode())
                .item(productDTO.getItem())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .brand(productDTO.getBrand())
                .build());

        return ResponseEntity.created(new URI("/api/products")).build();
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO){

        Optional<Product> productOptional = productService.findById(id);

        if(productOptional.isPresent()){
            Product product = productOptional.get();
            product.setProductCode(productDTO.getProductCode());
            product.setItem(productDTO.getItem());
            product.setDescription(productDTO.getDescription());
            product.setPrice(productDTO.getPrice());
            product.setBrand(productDTO.getBrand());
            productService.createProduct(product);
            return ResponseEntity.ok("Registro Actualizado");
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/products/{id}")
    public void deleteProductById(@PathVariable Long id){
        productService.deleteById(id);
    }
}
