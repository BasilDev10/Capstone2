package com.example.capstone.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Error: username is empty!")
    @Size(min = 4, max = 50, message = "Error: username length must be between 4 and 50 characters")
    @Column(columnDefinition = "VARCHAR(50) NOT NULL UNIQUE")
    private String username;

    @NotEmpty(message = "Error: password is empty!")
    @Size(min = 8, message = "Error: password length must be at least 8 characters")
    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String password;

    @Column(columnDefinition = "double default 0")
    private Double totalPaidAmount;

    @NotEmpty(message = "Error: role is empty!")
    @Pattern(regexp = "leader|member", message = "Error: role must be either 'leader' or 'member'")
    @Column(columnDefinition = "VARCHAR(10) NOT NULL")
    private String role;
    @NotNull(message = "Error: groupSavingAccountId is null")
    @Column(columnDefinition = "int not null")
    private Integer groupSavingAccountId;

}