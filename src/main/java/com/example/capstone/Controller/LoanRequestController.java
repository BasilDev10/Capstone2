package com.example.capstone.Controller;

import com.example.capstone.ApiResponse.ApiResponse;
import com.example.capstone.Model.LoanRequest;
import com.example.capstone.Service.LoanRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loan-request")
@RequiredArgsConstructor
public class LoanRequestController {

    private final LoanRequestService loanRequestService;

    @GetMapping("/get")
    public ResponseEntity<List<LoanRequest>> getAllLoanRequests() {
        return ResponseEntity.ok(loanRequestService.getAllLoanRequests());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<LoanRequest> getLoanRequestById(@PathVariable Integer id) {
        return ResponseEntity.ok(loanRequestService.getLoanRequestById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addLoanRequest(@RequestBody @Valid LoanRequest loanRequest, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        loanRequestService.addLoanRequest(loanRequest);
        return ResponseEntity.status(201).body(new ApiResponse("LoanRequest added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateLoanRequest(
            @PathVariable Integer id,
            @RequestBody @Valid LoanRequest loanRequest,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        loanRequestService.updateLoanRequest(id, loanRequest);
        return ResponseEntity.ok(new ApiResponse("LoanRequest updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteLoanRequest(@PathVariable Integer id) {
        loanRequestService.deleteLoanRequest(id);
        return ResponseEntity.ok(new ApiResponse("LoanRequest deleted successfully"));
    }
}
