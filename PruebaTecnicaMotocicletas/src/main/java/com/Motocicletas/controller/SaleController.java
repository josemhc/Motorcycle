package com.Motocicletas.controller;

import com.Motocicletas.DTO.request.SaleDTO;
import com.Motocicletas.DTO.response.SaleResponseDTO;
import com.Motocicletas.model.Sale;
import com.Motocicletas.service.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class SaleController {

    @Autowired
    private ISaleService saleService;

    @GetMapping("/sales/{id}")
    public ResponseEntity<SaleResponseDTO> getSaleById (@PathVariable Long id) {
        SaleResponseDTO saleDTO = saleService.getSaleById(id);
        return new ResponseEntity<>(saleDTO, HttpStatus.OK);
    }

    @PostMapping("/sales")
    public ResponseEntity<Sale> saveSale (@RequestBody SaleDTO saleDTO) {
        Sale saleSaved = saleService.createSale(saleDTO);
        return new ResponseEntity<>(saleSaved, HttpStatus.CREATED);
    }
}
