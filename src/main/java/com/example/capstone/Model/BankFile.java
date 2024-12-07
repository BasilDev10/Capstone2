package com.example.capstone.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BankFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Error: Name is required!")
    @Size(min = 1, max = 50, message = "Error: Name must be between 1 and 50 characters")
    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String name;

    @NotEmpty(message = "Error: Path is required!")
    @Size(max = 200, message = "Error: Path must not exceed 200 characters")
    @Column(columnDefinition = "VARCHAR(200) NOT NULL")
    private String path;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "group_saving_account_id", referencedColumnName = "id", nullable = true,
            foreignKey = @ForeignKey(name = "fk_bank_file_group_saving_account"))
    private GroupSavingAccount groupSavingAccount;
    @JsonIgnore
    @OneToMany(mappedBy = "bankFile", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false)
    private List<Transaction> transactions = new ArrayList<>();
}
