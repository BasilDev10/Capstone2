package com.example.capstone.Controller;

import com.example.capstone.Service.GroupSavingAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/group-saving-account")
@RequiredArgsConstructor
public class GroupSavingAccountController {


    private final GroupSavingAccountService groupSavingAccountService;

    @GetMapping("/get")
    public ResponseEntity getTransactions(){
        return ResponseEntity.ok(groupSavingAccountService.getTransactions());
    }
}
