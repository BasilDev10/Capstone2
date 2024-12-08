package com.example.capstone.Repository;

import com.example.capstone.Model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {

    Loan findLoanById(Integer id);

    Loan findLoanByLoanRequestId(Integer loanRequestId);

    @Query("select l from Loan l where l.userId=?1 and l.groupSavingAccountId =?2 and l.loanDate =?3 and l.amount =?4 and l.transactionId = null")
    Loan getLoanByUserIdAndGroupSavingAccountIdAndLoanDateAndAmount(Integer userId, Integer groupSavingAccountId, LocalDate loanDate, Double amount);
}
