package com.example.capstone.Service;

import com.example.capstone.ApiResponse.ApiException;
import com.example.capstone.Model.*;
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
    private final UserService userService;
    private final AccountSettingService accountSettingService;
    private final GroupSavingAccountService groupSavingAccountService;

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Loan getLoanById(Integer id) {
        return loanRepository.findLoanById(id);
    }

    public Loan getLoanByLoanRequestId(Integer id){
        return loanRepository.findLoanByLoanRequestId(id);
    }
    public Loan getLoanByUserIdAndGroupSavingAccountIdAndLoanDateAndAmount(Integer userId, Integer groupSavingAccountId, LocalDate loanDate, Double amount){
        return loanRepository.getLoanByUserIdAndGroupSavingAccountIdAndLoanDateAndAmount(userId,groupSavingAccountId,loanDate,amount);

    }

    public void addLoan(Loan loan) {
        validateLoan(loan.getUserId() ,loan.getAmount());
        loan = loanRepository.save(loan);

        createPaymentScheduleForLoan(loan.getId());
        groupSavingAccountService.updateBalance(loan.getGroupSavingAccountId());
        groupSavingAccountService.updatePaymentSchedule(loan.getGroupSavingAccountId());
    }

    public void validateLoan(Integer userId , Double amount){

        User user = userService.getUserById(userId);

        groupSavingAccountService.updateBalance(user.getGroupSavingAccountId());
        groupSavingAccountService.updatePaymentSchedule(user.getGroupSavingAccountId());

        GroupSavingAccount groupSavingAccount = groupSavingAccountService.getGroupSavingAccountById(user.getGroupSavingAccountId());



        AccountSetting accountSetting = accountSettingService.getAccountSettingById(user.getGroupSavingAccountId());

        double allowedLoanAmount = groupSavingAccount.getBalance() * ((double) accountSetting.getPercentageLonaAllowed() /100);



        if (amount > allowedLoanAmount) throw new ApiException("Error : must loan on range allowed loan amount :"+allowedLoanAmount);

        if (amount > user.getTotalPaidAmount() && user.getTotalPaidAmount()  < groupSavingAccount.getTargetMemberTotalPayment()) throw new ApiException("Error : user can't take a loan if loan amount greater than user payment and user not completed total payment in monthly payment schedule. for this case user only allowed take loan on range payment amount");




    }

    public void createPaymentScheduleForLoan(Integer loanId) {
        Loan loan = loanRepository.findLoanById(loanId);

        if (loan == null) throw new ApiException("Error: Loan not found");

        LocalDate installmentDate = loan.getStartInstallmentDate();
        PaymentSchedule paymentSchedule = new PaymentSchedule();
        for (int i = 0; i < loan.getInstallmentMonths(); i++) {
            paymentSchedule = new PaymentSchedule();

            if (i == 0) paymentSchedule.setScheduleDate(installmentDate);
            else paymentSchedule.setScheduleDate(installmentDate.plusMonths(i));

            paymentSchedule.setAmount(loan.getAmount() / loan.getInstallmentMonths());
            paymentSchedule.setScheduleCreatedType("system");
            paymentSchedule.setPaidAmount(0.0);
            paymentSchedule.setPaymentType("loan");
            paymentSchedule.setStatus("not paid");
            paymentSchedule.setLoanId(loan.getId());
            paymentSchedule.setUserId(loan.getUserId());
            paymentSchedule.setGroupSavingAccountId(loan.getGroupSavingAccountId());
            paymentScheduleService.addPaymentSchedule(paymentSchedule);
        }


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
