package com.Motocicletas.dto.sale.response;

import com.Motocicletas.dto.saledetails.response.SaleDetailResponseDTO;
import com.Motocicletas.model.Customer;
import com.Motocicletas.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SaleResponseDTO {

        private Long saleId;
        private Customer customer;
        private Employee employee;
        private double total;
        private List<SaleDetailResponseDTO> details;
}
