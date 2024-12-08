package com.example.capstone.Repository;

import com.example.capstone.Model.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Integer> {

    Expenses findExpensesById(Integer id);


    @Query("select e from Expenses e where e.userId =?1 and e.groupSavingAccountId =?2 and e.expensesDate =?3 and e.amount =?4 and e.transactionId = null")
    Expenses getExpensesByUserIdAndGroupSavingAccountIdAndExpensesDateAndAmount(
            Integer userId, Integer groupSavingAccountId, LocalDate expensesDate, Double amount);
}
