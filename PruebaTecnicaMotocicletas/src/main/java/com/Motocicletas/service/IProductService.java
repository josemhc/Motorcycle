package com.Motocicletas.service;

import com.Motocicletas.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IProductService {
    Product findById (Long id);

    List<Product> findAll ();

    Product createProduct(Product product);

    void deleteById(Long id);
}
