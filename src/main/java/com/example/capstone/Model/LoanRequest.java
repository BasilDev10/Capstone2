package com.example.capstone.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Check(constraints = "status IN ('pending', 'approved', 'rejected')")
public class LoanRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Error: Amount is null!")
    @Positive(message = "Error: Amount must be positive!")
    @Column(columnDefinition = "DOUBLE NOT NULL")
    private Double amount;

    @NotNull(message = "Error: LoanDate is null!")
    @Column(columnDefinition = "DATE NOT NULL")
    private LocalDate loanDate;

    @NotNull(message = "Error: StartInstallmentDate is null!")
    @Column(columnDefinition = "DATE NOT NULL")
    private LocalDate startInstallmentDate;

    @NotNull(message = "Error: InstallmentMonths is null!")
    @Positive(message = "Error: InstallmentMonths must be positive!")
    @Column(columnDefinition = "INT NOT NULL")
    private Integer installmentMonths;

    @NotEmpty(message = "Error: Reason is empty!")
    @Column(columnDefinition = "VARCHAR(300) NOT NULL")
    private String reason;

    @NotEmpty(message = "Error: Status is empty!")
    @Pattern(regexp = "pending|approved|rejected", message = "Error: status only accept pending or approved or rejected")
    @Column(columnDefinition = "VARCHAR(25) NOT NULL")
    private String status;

    @NotNull(message = "Error: groupSavingAccountId is null")
    @Column(columnDefinition = "int not null")
    private Integer groupSavingAccountId;


    private Integer userId;
}
