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
@Check(constraints = "status IN ('paid', 'partial paid', 'not paid') and type IN ('group', 'personal')")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Error: Amount is null!")
    @Positive(message = "Error: Amount must be positive!")
    @Column(columnDefinition = "double NOT NULL")
    private Double amount;

    @Positive(message = "Error: PaidAmount must be positive!")
    @Column(columnDefinition = "double DEFAULT 0")
    private Double paidAmount = 0.0;

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

    @NotEmpty(message = "Error: Type is empty!")
    @Pattern(regexp = "group|personal" , message = "Error: type only accept group or personal")
    @Column(columnDefinition = "VARCHAR(25) NOT NULL ")
    private String loanType;

    @NotEmpty(message = "Error: Status is empty!")
    @Pattern(regexp = "paid|partial paid|not paid", message = "Error: status only accept paid or partial paid or not paid")
    @Column(columnDefinition = "VARCHAR(25) NOT NULL )")
    private String status;

    @ManyToOne
    @JoinColumn(name = "groupSavingAccountId", nullable = true)
    private GroupSavingAccount groupSavingAccount;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "transactionId", nullable = true)
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "loanRequestId", nullable = true)
    private LoanRequest loanRequest;
}
