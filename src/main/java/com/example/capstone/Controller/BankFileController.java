package com.example.capstone.Controller;

import com.example.capstone.ApiResponse.ApiResponse;
import com.example.capstone.Model.BankFile;
import com.example.capstone.Service.BankFileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/bank-file")
@RequiredArgsConstructor
public class BankFileController {

    private final BankFileService bankFileService;

    @GetMapping("/get")
    public ResponseEntity getAllBankFiles() {
        return ResponseEntity.ok(bankFileService.getAllBankFiles());
    }

    @PostMapping("/add/{groupSavingAccountId}")
    public ResponseEntity addBankFile(@RequestParam("file") MultipartFile file , @PathVariable Integer groupSavingAccountId) {


        bankFileService.addBankFile(file , groupSavingAccountId);
        return ResponseEntity.status(201).body(new ApiResponse("BankFile added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateBankFile(@PathVariable Integer id, @RequestBody @Valid BankFile bankFile, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        bankFileService.updateBankFile(id, bankFile);
        return ResponseEntity.ok(new ApiResponse("BankFile updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteBankFile(@PathVariable Integer id) {
        bankFileService.deleteBankFile(id);
        return ResponseEntity.ok(new ApiResponse("BankFile deleted successfully"));
    }
}
