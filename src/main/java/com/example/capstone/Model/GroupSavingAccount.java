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

    @NotNull(message = "Error: targetBalance is null!")
    @Column(columnDefinition = "double not null")
    private Double targetBalance;

    @NotNull(message = "Error: overdueAmount  is null!")
    @Column(columnDefinition = "double not null")
    private Double overdueAmount ;

    @NotNull(message = "Error: transactionDate is null")
    @Column(columnDefinition = "date not null")
    private LocalDate StartDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "account_setting_id", referencedColumnName = "id", nullable = true,
            foreignKey = @ForeignKey(name = "fk_group_saving_account_account_setting"))
    private AccountSetting accountSetting;

    @JsonIgnore
    @OneToMany(mappedBy = "groupSavingAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BankFile> bankFiles = new ArrayList<>();


    @JsonIgnore
    @OneToMany(mappedBy = "groupSavingAccount", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false)
    private List<Transaction> transactions = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "groupSavingAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users = new ArrayList<>();


}
