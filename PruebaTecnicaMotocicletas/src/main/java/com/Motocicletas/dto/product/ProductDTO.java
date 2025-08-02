package com.Motocicletas.dto.product;

import com.Motocicletas.model.SaleDetail;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDTO {
    private Long id;
    private String productCode;
    private String item;
    private String description;
    private double price;
    private String brand;
    private List<SaleDetail> saleDetails;
}
