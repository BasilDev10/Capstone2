package com.example.capstone.Service;

import com.example.capstone.ApiResponse.ApiException;
import com.example.capstone.Model.Loan;
import com.example.capstone.Model.LoanRequest;
import com.example.capstone.Model.User;
import com.example.capstone.Repository.LoanRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanRequestService {

    private final LoanRequestRepository loanRequestRepository;
    private final LoanService loanService;
    private final UserService userService;

    public List<LoanRequest> getAllLoanRequests() {
        return loanRequestRepository.findAll();
    }

    public LoanRequest getLoanRequestById(Integer id) {
        return loanRequestRepository.findLoanRequestById(id);
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

    public void updateStatus(Integer id, Integer leaderId , String status){
        User leaderUser = userService.getUserById(leaderId);
        LoanRequest loanRequest = getLoanRequestById(id);
        if (loanRequest == null) throw new ApiException("Error: LoanRequest not found");
        if (leaderUser == null) throw new ApiException("Error: user not found");
        if (!leaderUser.getRole().equalsIgnoreCase("leader")) throw new ApiException("Error: user not allowed update status");
        if (loanRequest.getStatus().equalsIgnoreCase("approved")||loanRequest.getStatus().equalsIgnoreCase("rejected")) throw new ApiException("Error: Loan request is already approved or rejected");

        loanRequest.setStatus(status);
        loanRequestRepository.save(loanRequest);

        if (status.equalsIgnoreCase("approved")){


            convertLonaRequestToLoan(loanRequest.getId());
        }
    }
    public void convertLonaRequestToLoan(Integer id){
        LoanRequest loanRequest = getLoanRequestById(id);
        if (loanService.getLoanByLoanRequestId(loanRequest.getId()) != null) throw new ApiException("Error: Loan already created");
        if (loanRequest.getStatus().equalsIgnoreCase("approved")) throw new ApiException("Error: loanRequest not approved or rejected");
        loanService.validateLoan(loanRequest.getUser());


        Loan loan = new Loan();

        loan.setLoanRequest(loanRequest);
        loan.setLoanDate(loanRequest.getLoanDate());
        loan.setInstallmentMonths(loanRequest.getInstallmentMonths());
        loan.setStartInstallmentDate(loanRequest.getStartInstallmentDate());
        loan.setLoanType("personal");
        loan.setStatus("not paid");
        loan.setAmount(loanRequest.getAmount());
        loan.setGroupSavingAccount(loanRequest.getGroupSavingAccount());
        loan.setUser(loanRequest.getUser());

        loanService.addLoan(loan);

    }
    public void deleteLoanRequest(Integer id) {
        if (loanRequestRepository.findLoanRequestById(id) == null) {
            throw new ApiException("Error: LoanRequest not found!");
        }
        loanRequestRepository.deleteById(id);
    }
}
