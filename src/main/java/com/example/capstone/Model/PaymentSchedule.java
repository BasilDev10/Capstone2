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
import org.hibernate.annotations.DialectOverride;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Check(constraints = "status IN ('paid', 'partial paid', 'not paid') and paymentType IN ('loan', 'monthlyPayment')")
public class PaymentSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Error: Amount is null!")
    @Positive(message = "Error: Amount must be positive!")
    @Column(columnDefinition = "DOUBLE NOT NULL")
    private Double amount;

    @NotEmpty(message = "Error: paymentType is empty!")
    @Pattern(regexp = "loan|monthlyPayment" , message = "Error: paymentType only accept loan or monthlyPayment")
    @Column(columnDefinition = "VARCHAR(20) NOT NULL ")
    private String paymentType;

    @NotEmpty(message = "Error: scheduleCreatedType is empty!")
    @Pattern(regexp = "system|manually" , message = "Error: scheduleCreatedType only system loan or manually")
    @Column(columnDefinition = "VARCHAR(20) NOT NULL ")
    private String scheduleCreatedType;

    @NotNull(message = "Error: ScheduleDate is null!")
    @Column(columnDefinition = "DATE NOT NULL")
    private LocalDate scheduleDate;

    @NotEmpty(message = "Error: Status is empty!")
    @Pattern(regexp = "paid|partial paid|not paid", message = "Error: status only accept paid or partial paid or not paid")
    @Column(columnDefinition = "VARCHAR(20) NOT NULL")
    private String status;

    @ManyToOne
    @JoinColumn(name = "loanId", nullable = true)
    private Loan loan;

    @ManyToOne
    @JoinColumn(name = "groupSavingAccountId", nullable = true)
    private GroupSavingAccount groupSavingAccount;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = true)
    private User user;
}
