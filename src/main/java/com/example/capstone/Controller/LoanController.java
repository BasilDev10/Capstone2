package com.example.capstone.Controller;

import com.example.capstone.ApiResponse.ApiResponse;
import com.example.capstone.Model.Loan;
import com.example.capstone.Service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loan")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @GetMapping("/get")
    public ResponseEntity<List<Loan>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Integer id) {
        return ResponseEntity.ok(loanService.getLoanById(id));
    }

    //endpoint 5
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addLoan(@RequestBody @Valid Loan loan) {
        loanService.addLoan(loan);
        return ResponseEntity.status(201).body(new ApiResponse("Loan added successfully"));
    }

    // endpoint 6
    @PostMapping("/create-payment-schedule-for-loan/{id}")
    public ResponseEntity<ApiResponse> createPaymentScheduleForLoan(@PathVariable Integer id){
        loanService.createPaymentScheduleForLoan(id);
        return ResponseEntity.ok(new ApiResponse("Payment Schedule created for loan successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateLoan(@PathVariable Integer id, @RequestBody @Valid Loan loan) {
        loanService.updateLoan(id, loan);
        return ResponseEntity.ok(new ApiResponse("Loan updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteLoan(@PathVariable Integer id) {
        loanService.deleteLoan(id);
        return ResponseEntity.ok(new ApiResponse("Loan deleted successfully"));
    }
}
