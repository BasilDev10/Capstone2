package com.example.capstone.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Expenses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Error: Amount is null!")
    @Positive(message = "Error: Amount must be positive!")
    @Column(columnDefinition = "double NOT NULL")
    private Double amount;

    @NotNull(message = "Error: ExpensesDate is null!")
    @Column(columnDefinition = "DATE NOT NULL")
    private LocalDate expensesDate;

    @NotEmpty(message = "Error: Type is empty!")
    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String type;

    @ManyToOne
    @JoinColumn(name = "groupSavingAccountId", nullable = true)
    private GroupSavingAccount groupSavingAccount;

    @ManyToOne
    @JoinColumn(name = "transactionId", nullable = true)
    private Transaction transaction;
}
