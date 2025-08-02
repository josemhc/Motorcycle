package com.Motocicletas.service;

import com.Motocicletas.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    List<Product> findAll ();

    Optional<Product> findById (Long id);

    void createProduct(Product product);

    void deleteById(Long id);
}
