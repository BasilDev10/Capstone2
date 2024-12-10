package com.example.capstone.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserBankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Error: Account Number is empty!")
    @Column(columnDefinition = "VARCHAR(100) NOT NULL unique")
    private String accountNumber;

    @NotNull(message = "Error: userId is null")
    @Column(columnDefinition = "int not null")
    private Integer userId;
}
