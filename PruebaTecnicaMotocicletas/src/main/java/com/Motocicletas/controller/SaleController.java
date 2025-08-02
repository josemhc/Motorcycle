package com.Motocicletas.controller;

import com.Motocicletas.dto.sale.request.SaleDTO;
import com.Motocicletas.dto.sale.response.SaleResponseDTO;
import com.Motocicletas.model.Sale;
import com.Motocicletas.service.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class SaleController {

    @Autowired
    private ISaleService saleService;

    @GetMapping("/sales")
    public ResponseEntity<List<SaleResponseDTO>> getSales () {
        List<SaleResponseDTO> saleDTO = saleService.getSales();
        return new ResponseEntity<>(saleDTO, HttpStatus.OK);
    }

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

    @DeleteMapping("/sales/{id}")
    public ResponseEntity<Void> deleteSaleById (@PathVariable Long id) {
        saleService.deleteSale(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
