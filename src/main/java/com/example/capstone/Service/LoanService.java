package com.example.capstone.Service;

import com.example.capstone.ApiResponse.ApiException;
import com.example.capstone.Model.Loan;
import com.example.capstone.Repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Loan getLoanById(Integer id) {
        Loan loan = loanRepository.findLoanById(id);
        if (loan == null) {
            throw new ApiException("Error: Loan not found!");
        }
        return loan;
    }

    public void addLoan(Loan loan) {
        loanRepository.save(loan);
    }

    public void updateLoan(Integer id, Loan loan) {
        if (loanRepository.findLoanById(id) == null) {
            throw new ApiException("Error: Loan not found!");
        }
        loan.setId(id);
        loanRepository.save(loan);
    }

    public void deleteLoan(Integer id) {
        if (loanRepository.findLoanById(id) == null) {
            throw new ApiException("Error: Loan not found!");
        }
        loanRepository.deleteById(id);
    }
}
