package com.example.capstone.Controller;

import com.example.capstone.ApiResponse.ApiResponse;
import com.example.capstone.Model.GroupSavingAccount;
import com.example.capstone.Service.GroupSavingAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/group-saving-account")
@RequiredArgsConstructor
public class GroupSavingAccountController {


    private final GroupSavingAccountService groupSavingAccountService;


    @GetMapping("/get")
    public ResponseEntity getAllGroupSavingAccounts() {
        return ResponseEntity.ok(groupSavingAccountService.getAllGroupSavingAccounts());
    }

    @PostMapping("/add")
    public ResponseEntity addGroupSavingAccount(@RequestBody @Valid GroupSavingAccount groupSavingAccount , Errors errors) {
        if (errors.hasErrors()) return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        groupSavingAccountService.addGroupSavingAccount(groupSavingAccount);
        return ResponseEntity.status(201).body(new ApiResponse("Group account saving added successfully"));
    }
    @PostMapping("/create-monthly-payment-schedule/{id}")
    public ResponseEntity<ApiResponse> createMonthlyPaymentSchedule(@PathVariable Integer id){

        groupSavingAccountService.createMonthlyPaymentSchedule(id);
        return ResponseEntity.ok(new ApiResponse("Monthly Payment schedule is created"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateGroupSavingAccount(@PathVariable Integer id, @RequestBody @Valid GroupSavingAccount groupSavingAccount , Errors errors) {
        if (errors.hasErrors()) return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        groupSavingAccountService.updateGroupSavingAccount(id, groupSavingAccount);
        return ResponseEntity.ok(new ApiResponse("Group account saving updated successfully"));
    }

    @PutMapping("/update-balance/{id}")
    public ResponseEntity updateBalance(@PathVariable Integer id){

        groupSavingAccountService.updateBalance(id);
        return ResponseEntity.ok(new ApiResponse("Group account saving Balance is been updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteGroupSavingAccount(@PathVariable Integer id) {
        groupSavingAccountService.deleteGroupSavingAccount(id);
        return ResponseEntity.ok(new ApiResponse("Group account saving deleted successfully"));
    }
}
