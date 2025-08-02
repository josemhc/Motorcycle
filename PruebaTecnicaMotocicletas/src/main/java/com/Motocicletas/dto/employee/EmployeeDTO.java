package com.Motocicletas.dto.employee;

import com.Motocicletas.model.Sale;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeDTO {
    private Long id;
    private String documentType;
    private String documentNumber;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String position;
    private String email;
    private String phoneNumber;
    private String address;
    private List<Sale> sales;
}
