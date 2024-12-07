package com.example.capstone.Controller;

import com.example.capstone.ApiResponse.ApiResponse;
import com.example.capstone.Model.AccountSetting;
import com.example.capstone.Service.AccountSettingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account-setting")
@RequiredArgsConstructor
public class AccountSettingController {

    private final AccountSettingService accountSettingService;

    @GetMapping("/get")
    public ResponseEntity<List<AccountSetting>> getAllAccountSettings() {
        return ResponseEntity.ok(accountSettingService.getAllAccountSettings());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<AccountSetting> getAccountSettingById(@PathVariable Integer id) {
        AccountSetting accountSetting = accountSettingService.getAccountSettingById(id);
        if (accountSetting == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(accountSetting);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addAccountSetting(@RequestBody @Valid AccountSetting accountSetting, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        accountSettingService.addAccountSetting(accountSetting);
        return ResponseEntity.status(201).body(new ApiResponse("AccountSetting added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateAccountSetting(
            @PathVariable Integer id,
            @RequestBody @Valid AccountSetting accountSetting,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        accountSettingService.updateAccountSetting(id, accountSetting);
        return ResponseEntity.ok(new ApiResponse("AccountSetting updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteAccountSetting(@PathVariable Integer id) {
        accountSettingService.deleteAccountSetting(id);
        return ResponseEntity.ok(new ApiResponse("AccountSetting deleted successfully"));
    }
}
