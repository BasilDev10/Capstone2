package com.example.capstone.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Error: Amount is null!")
    @Positive(message = "Error: Amount must be positive!")
    @Column(columnDefinition = "double ")
    private Double amount;

    @PositiveOrZero(message = "Error: PaidAmount must be positive or zero!")
    @Column(columnDefinition = "double default 0 ")
    private Double paidAmount;

    @NotNull(message = "Error: LoanDate is null!")
    @Column(columnDefinition = "DATE NOT NULL")
    private LocalDate loanDate;

    @NotNull(message = "Error: StartInstallmentDate is null!")
    @Column(columnDefinition = "DATE NOT NULL")
    private LocalDate startInstallmentDate;

    @NotNull(message = "Error: InstallmentMonths is null!")
    @Positive(message = "Error: InstallmentMonths must be positive!")
    @Column(columnDefinition = "int NOT NULL")
    private Integer installmentMonths;

    @NotEmpty(message = "Error: Type is empty!")
    @Pattern(regexp = "group|personal" , message = "Error: type only accept group or personal")
    @Column(columnDefinition = "VARCHAR(25) NOT NULL ")
    private String loanType;

    @NotEmpty(message = "Error: Status is empty!")
    @Pattern(regexp = "paid|partial paid|not paid", message = "Error: status only accept paid or partial paid or not paid")
    @Column(columnDefinition = "VARCHAR(25) NOT NULL ")
    private String status;

    @NotNull(message = "Error: groupSavingAccountId is null")
    @Column(columnDefinition = "int not null")
    private Integer groupSavingAccountId;

    @NotNull(message = "Error: userId is null")
    @Column(columnDefinition = "int not null")
    private Integer userId;

    @Column(columnDefinition = "int ")
    private Integer transactionId;

    @Column(columnDefinition = "int ")
    private Integer loanRequestId;
}
