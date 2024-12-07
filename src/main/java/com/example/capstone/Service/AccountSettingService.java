package com.example.capstone.Service;

import com.example.capstone.ApiResponse.ApiException;
import com.example.capstone.Model.AccountSetting;
import com.example.capstone.Repository.AccountSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountSettingService {

    private final AccountSettingRepository accountSettingRepository;

    public List<AccountSetting> getAllAccountSettings() {
        return accountSettingRepository.findAll();
    }

    public AccountSetting getAccountSettingById(Integer id) {
        return accountSettingRepository.findAccountSettingById(id);
    }

    public void addAccountSetting(AccountSetting accountSetting) {
        accountSettingRepository.save(accountSetting);
    }

    public void updateAccountSetting(Integer id, AccountSetting accountSetting) {
        if (accountSettingRepository.findAccountSettingById(id) == null)
            throw new ApiException("Error: AccountSetting not found");

        accountSetting.setId(id);
        accountSettingRepository.save(accountSetting);
    }

    public void deleteAccountSetting(Integer id) {
        if (accountSettingRepository.findAccountSettingById(id) == null)
            throw new ApiException("Error: AccountSetting not found");

        accountSettingRepository.deleteById(id);
    }
}
