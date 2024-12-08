package com.example.capstone.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Error: Transaction type is required!")
    @Column(columnDefinition = "VARCHAR(300) NOT NULL ")
    private String transactionDetails;

    @NotEmpty(message = "Error: Transaction type is required!")
    @Pattern(regexp = "Credit|Debit", message = "Error: transactionType only accept Credit or Debit")
    @Column(columnDefinition = "VARCHAR(25) NOT NULL ")
    private String transactionType;

    @NotNull(message = "Error: Transaction date is required!")
    @Column(columnDefinition = "DATE NOT NULL")
    private LocalDate transactionDate;

    @NotNull(message = "Error: amount is null")
    @Positive(message = "Error: amount must be positive")
    @Column(columnDefinition = "double not null")
    private Double amount;



    private Integer userId;

    private Integer groupSavingAccountId;

    private Integer bankFileId;
}
