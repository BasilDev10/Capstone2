package com.example.capstone.Repository;

import com.example.capstone.Model.AccountSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountSettingRepository extends JpaRepository<AccountSetting, Integer> {
    AccountSetting findAccountSettingById(Integer id);
}
