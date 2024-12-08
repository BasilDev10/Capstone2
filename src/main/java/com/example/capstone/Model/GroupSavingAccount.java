package com.example.capstone.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class GroupSavingAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotEmpty(message = "Error: name is empty!")
    @Size(min = 4 ,message = "Error: name length must be more 4")
    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String name;

    @NotNull(message = "Error: balance is null!")
    @Column(columnDefinition = "double not null")
    private Double balance;

    @NotNull(message = "Error: targetMemberTotalPayment is null!")
    @Column(columnDefinition = "double not null")
    private Double targetAccountTotalPayment;


    private Double targetMemberTotalPayment;
    @NotNull(message = "Error: overdueAmount  is null!")
    @Column(columnDefinition = "double not null")
    private Double overdueAmount ;

    @NotNull(message = "Error: transactionDate is null")
    @Column(columnDefinition = "date not null")
    private LocalDate StartDate;


    private Integer accountSettingId;




}
