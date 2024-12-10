package com.example.capstone.Controller;

import com.example.capstone.ApiResponse.ApiResponse;
import com.example.capstone.Model.Transaction;
import com.example.capstone.Service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/get")
    public ResponseEntity getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @PostMapping("/add")
    public ResponseEntity addTransaction(@RequestBody @Valid Transaction transaction) {
        transactionService.addTransaction(transaction);
        return ResponseEntity.status(201).body(new ApiResponse("Transaction added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateTransaction(@PathVariable Integer id, @RequestBody @Valid Transaction transaction) {
        transactionService.updateTransaction(id, transaction);
        return ResponseEntity.ok(new ApiResponse("Transaction updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTransaction(@PathVariable Integer id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.ok(new ApiResponse("Transaction deleted successfully"));
    }
}
