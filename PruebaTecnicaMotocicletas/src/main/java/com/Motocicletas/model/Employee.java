package com.Motocicletas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="employee")
public class Employee {

    // De un empleado se tiene la información: tipo de documento de identidad, número de
    // identidad, nombres, apellidos, fecha de nacimiento, cargo, email, celular, dirección.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private List<Sale> sales;

}
