package com.example.capstone.Repository;

import com.example.capstone.Model.UserBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBankAccountRepository extends JpaRepository<UserBankAccount, Integer> {


    UserBankAccount findUserBankAccountById(Integer id);


}
