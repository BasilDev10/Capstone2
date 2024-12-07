package com.example.capstone.Service;

import com.example.capstone.ApiResponse.ApiException;
import com.example.capstone.Model.LoanRequest;
import com.example.capstone.Repository.LoanRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanRequestService {

    private final LoanRequestRepository loanRequestRepository;

    public List<LoanRequest> getAllLoanRequests() {
        return loanRequestRepository.findAll();
    }

    public LoanRequest getLoanRequestById(Integer id) {
        LoanRequest loanRequest = loanRequestRepository.findLoanRequestById(id);
        if (loanRequest == null) {
            throw new ApiException("Error: LoanRequest not found!");
        }
        return loanRequest;
    }

    public void addLoanRequest(LoanRequest loanRequest) {
        loanRequestRepository.save(loanRequest);
    }

    public void updateLoanRequest(Integer id, LoanRequest loanRequest) {
        if (loanRequestRepository.findLoanRequestById(id) == null) {
            throw new ApiException("Error: LoanRequest not found!");
        }
        loanRequest.setId(id);
        loanRequestRepository.save(loanRequest);
    }

    public void deleteLoanRequest(Integer id) {
        if (loanRequestRepository.findLoanRequestById(id) == null) {
            throw new ApiException("Error: LoanRequest not found!");
        }
        loanRequestRepository.deleteById(id);
    }
}
