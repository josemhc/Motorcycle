package com.Motocicletas.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class SaleDetailResponseDTO {
        private String productCode;
        private String brand;
        private double price;
        private int quantity;
        private double subtotal;
}
