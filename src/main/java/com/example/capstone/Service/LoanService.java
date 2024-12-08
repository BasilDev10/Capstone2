package com.example.capstone.Service;

import com.example.capstone.ApiResponse.ApiException;
import com.example.capstone.Model.Loan;
import com.example.capstone.Model.LoanRequest;
import com.example.capstone.Model.PaymentSchedule;
import com.example.capstone.Model.User;
import com.example.capstone.Repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final PaymentScheduleService paymentScheduleService;

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Loan getLoanById(Integer id) {
        return loanRepository.findLoanById(id);
    }

    public Loan getLoanByLoanRequestId(Integer id){
        return loanRepository.findLoanByLoanRequestId(id);
    }

    public void addLoan(Loan loan) {
        loan = loanRepository.save(loan);

        createPaymentScheduleForLoan(loan.getId());

    }

    public void validateLoan(User user){

    }

    public void createPaymentScheduleForLoan(Integer loanId) {
        Loan loan = loanRepository.findLoanById(loanId);

        if (loan == null) throw new ApiException("Error: Loan not found");

        LocalDate installmentDate = loan.getStartInstallmentDate();
        PaymentSchedule paymentSchedule = null;
        for (int i = 0; i < loan.getInstallmentMonths(); i++) {
            paymentSchedule = new PaymentSchedule();

            if (i == 0) paymentSchedule.setScheduleDate(installmentDate);
            else paymentSchedule.setScheduleDate(installmentDate.plusMonths(i));

            paymentSchedule.setAmount(loan.getAmount() / loan.getInstallmentMonths());
            paymentSchedule.setPaymentType("loan");
            paymentSchedule.setStatus("not paid");
            paymentSchedule.setLoan(loan);
            paymentSchedule.setUser(loan.getUser());
            paymentSchedule.setGroupSavingAccount(loan.getGroupSavingAccount());

        }

        paymentScheduleService.addPaymentSchedule(paymentSchedule);
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
