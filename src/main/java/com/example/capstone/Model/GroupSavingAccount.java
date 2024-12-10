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

    @Column(columnDefinition = "double default 0 ")
    private Double balance;

    @Column(columnDefinition = "double default 0 ")
    private Double targetAccountTotalPayment;

    @Column(columnDefinition = "double default 0 ")
    private Double targetMemberTotalPayment;

    @Column(columnDefinition = "double default 0 ")
    private Double overdueAmount ;

    @NotNull(message = "Error: transactionDate is null")
    @Column(columnDefinition = "date not null")
    private LocalDate StartDate;


    @Column(columnDefinition = "int")
    private Integer accountSettingId;




}
