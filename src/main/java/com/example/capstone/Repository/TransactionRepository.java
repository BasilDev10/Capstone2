package com.example.capstone.Repository;

import com.example.capstone.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Transaction findTransactionById(Integer id);
}
