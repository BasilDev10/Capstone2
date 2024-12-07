package com.example.capstone.Controller;

import com.example.capstone.ApiResponse.ApiResponse;
import com.example.capstone.Model.UserBankAccount;
import com.example.capstone.Service.UserBankAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-bank-account")
@RequiredArgsConstructor
public class UserBankAccountController {

    private final UserBankAccountService userBankAccountService;


    @GetMapping("/get")
    public ResponseEntity<List<UserBankAccount>> getAllUserBankAccounts() {
        return ResponseEntity.ok(userBankAccountService.getAllUserBankAccounts());
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<UserBankAccount> getUserBankAccountById(@PathVariable Integer id) {
        return ResponseEntity.ok(userBankAccountService.getUserBankAccountById(id));
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addUserBankAccount(@RequestBody @Valid UserBankAccount userBankAccount, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        userBankAccountService.addUserBankAccount(userBankAccount);
        return ResponseEntity.status(201).body(new ApiResponse("UserBankAccount added successfully"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateUserBankAccount(@PathVariable Integer id, @RequestBody @Valid UserBankAccount userBankAccount, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        userBankAccountService.updateUserBankAccount(id, userBankAccount);
        return ResponseEntity.ok(new ApiResponse("UserBankAccount updated successfully"));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUserBankAccount(@PathVariable Integer id) {
        userBankAccountService.deleteUserBankAccount(id);
        return ResponseEntity.ok(new ApiResponse("UserBankAccount deleted successfully"));
    }
}
