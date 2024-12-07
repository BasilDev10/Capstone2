package com.example.capstone.Controller;

import com.example.capstone.ApiResponse.ApiResponse;
import com.example.capstone.Model.Expenses;
import com.example.capstone.Service.ExpensesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expenses")
@RequiredArgsConstructor
public class ExpensesController {

    private final ExpensesService expensesService;

    @GetMapping("/get")
    public ResponseEntity<List<Expenses>> getAllExpenses() {
        return ResponseEntity.ok(expensesService.getAllExpenses());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Expenses> getExpensesById(@PathVariable Integer id) {
        return ResponseEntity.ok(expensesService.getExpensesById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addExpenses(@RequestBody @Valid Expenses expenses, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        expensesService.addExpenses(expenses);
        return ResponseEntity.status(201).body(new ApiResponse("Expenses added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateExpenses(
            @PathVariable Integer id,
            @RequestBody @Valid Expenses expenses,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        expensesService.updateExpenses(id, expenses);
        return ResponseEntity.ok(new ApiResponse("Expenses updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteExpenses(@PathVariable Integer id) {
        expensesService.deleteExpenses(id);
        return ResponseEntity.ok(new ApiResponse("Expenses deleted successfully"));
    }
}
