package com.example.capstone.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AccountSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Error: name is empty!")
    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String name;

    @NotNull(message = "Error: monthlyPayment is null!")
    @Column(columnDefinition = "double not null")
    private Double monthlyPayment;

    @PositiveOrZero(message = "Error: percentageLonaAllowed only positive or zero")
    @Column(columnDefinition = "int not null")
    private Integer percentageLonaAllowed;


    @NotNull(message = "Error: applyLoan is null!")
    @Column(columnDefinition = "boolean not null")
    private Boolean applyLoan;

    @NotNull(message = "Error: applyLoanRequest is null!")
    @Column(columnDefinition = "boolean not null")
    private Boolean applyLoanRequest;
}
