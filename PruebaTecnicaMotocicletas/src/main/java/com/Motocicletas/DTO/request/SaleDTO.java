package com.Motocicletas.DTO.request;

import lombok.Getter;

import java.util.List;

@Getter
public class SaleDTO {
    private Long clientId;
    private Long employeeId;
    private List<SaleDetailDTO> details;

}
