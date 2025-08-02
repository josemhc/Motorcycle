package com.Motocicletas.dto.sale.response;

import com.Motocicletas.dto.saledetails.response.SaleDetailResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SaleResponseDTO {

        private Long saleId;
        private String customerName;
        private String employeeName;
        private double total;
        private List<SaleDetailResponseDTO> details;
}
