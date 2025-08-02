package com.Motocicletas.service;

import com.Motocicletas.dto.sale.request.SaleDTO;
import com.Motocicletas.dto.sale.response.SaleResponseDTO;
import com.Motocicletas.model.Sale;

import java.util.List;

public interface ISaleService {
    Sale createSale(SaleDTO saleDTO);
    public SaleResponseDTO getSaleById (Long id);
    public List<SaleResponseDTO> getSales ();
    public void deleteSale (Long id);
}
