package com.Motocicletas.dto.sale.request;

import com.Motocicletas.dto.saledetails.request.SaleDetailDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class SaleDTO {
    private Long customerId;
    private Long employeeId;
    private List<SaleDetailDTO> details;

}
