package com.example.capstone.Repository;

import com.example.capstone.Model.BankFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankFileRepository extends JpaRepository<BankFile, Integer> {
    BankFile findBankFileById(Integer id);
}
