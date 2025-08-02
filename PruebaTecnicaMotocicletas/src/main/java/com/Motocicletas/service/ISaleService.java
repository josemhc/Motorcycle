package com.Motocicletas.service;

import com.Motocicletas.DTO.request.SaleDTO;
import com.Motocicletas.DTO.response.SaleResponseDTO;
import com.Motocicletas.model.Sale;

import java.util.List;

public interface ISaleService {
    Sale createSale(SaleDTO saleDTO);
    public SaleResponseDTO getSaleById (Long id);
    public List<SaleResponseDTO> getSales ();
    public void deleteSale (Long id);
}
