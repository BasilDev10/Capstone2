package com.example.capstone.Repository;

import com.example.capstone.Model.LoanRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRequestRepository extends JpaRepository<LoanRequest, Integer> {

    LoanRequest findLoanRequestById(Integer id);
}
