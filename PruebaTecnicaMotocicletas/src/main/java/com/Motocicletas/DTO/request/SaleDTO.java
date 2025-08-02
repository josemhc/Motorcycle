package com.Motocicletas.DTO.request;

import java.util.List;

public class SaleDTO {
    private Long clientId;
    private Long employeeId;
    private List<SaleDetailDTO> details;

    public Long getClientId() {
        return clientId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public List<SaleDetailDTO> getDetails() {
        return details;
    }

}
