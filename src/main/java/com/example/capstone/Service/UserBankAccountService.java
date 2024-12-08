package com.example.capstone.Service;

import com.example.capstone.ApiResponse.ApiException;
import com.example.capstone.Model.UserBankAccount;
import com.example.capstone.Repository.UserBankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserBankAccountService {

    private final UserBankAccountRepository userBankAccountRepository;


    public List<UserBankAccount> getAllUserBankAccounts() {
        return userBankAccountRepository.findAll();
    }

    public UserBankAccount getUserBankAccountByAccountNumber(String accountNumber) {

        List<UserBankAccount> getAllUserBankAccounts = userBankAccountRepository.findAll();

        for (UserBankAccount userBankAccount : getAllUserBankAccounts) {
            if (accountNumber.contains(userBankAccount.getAccountNumber())) {
                return userBankAccount;
            }
        }
        return null;
    }

    public UserBankAccount getUserBankAccountById(Integer id) {
        UserBankAccount userBankAccount = userBankAccountRepository.findUserBankAccountById(id);
        if (userBankAccount == null) {
            throw new ApiException("Error: UserBankAccount not found!");
        }
        return userBankAccount;
    }


    public void addUserBankAccount(UserBankAccount userBankAccount) {
        userBankAccountRepository.save(userBankAccount);
    }


    public void updateUserBankAccount(Integer id, UserBankAccount userBankAccount) {
        if (userBankAccountRepository.findUserBankAccountById(id) == null) {
            throw new ApiException("Error: UserBankAccount not found!");
        }
        userBankAccount.setId(id);
        userBankAccountRepository.save(userBankAccount);
    }


    public void deleteUserBankAccount(Integer id) {
        if (userBankAccountRepository.findUserBankAccountById(id) == null) {
            throw new ApiException("Error: UserBankAccount not found!");
        }
        userBankAccountRepository.deleteById(id);
    }
}
