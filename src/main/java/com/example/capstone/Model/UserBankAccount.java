package com.example.capstone.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "Error: Bank Name is empty!")
    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String bankName;

    @NotEmpty(message = "Error: Account Number is empty!")
    @Column(columnDefinition = "VARCHAR(100) NOT NULL")
    private String accountNumber;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true,
            foreignKey = @ForeignKey(name = "fk_user_bank_account_user"))
    private User user;
}
