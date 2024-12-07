package com.example.capstone.Repository;

import com.example.capstone.Model.GroupSavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupSavingAccountRepository extends JpaRepository<GroupSavingAccount, Integer> {

    GroupSavingAccount findGroupSavingAccountById(Integer id);
}
