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
        return ResponseEntity.status(201).body(new ApiResponse("GroupSavingAccount added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateGroupSavingAccount(@PathVariable Integer id, @RequestBody @Valid GroupSavingAccount groupSavingAccount , Errors errors) {
        if (errors.hasErrors()) return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        groupSavingAccountService.updateGroupSavingAccount(id, groupSavingAccount);
        return ResponseEntity.ok(new ApiResponse("GroupSavingAccount updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteGroupSavingAccount(@PathVariable Integer id) {
        groupSavingAccountService.deleteGroupSavingAccount(id);
        return ResponseEntity.ok(new ApiResponse("GroupSavingAccount deleted successfully"));
    }
}
