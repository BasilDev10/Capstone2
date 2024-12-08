package com.example.capstone.Service;

import com.example.capstone.ApiResponse.ApiException;
import com.example.capstone.Model.GroupSavingAccount;
import com.example.capstone.Model.PaymentSchedule;
import com.example.capstone.Model.Transaction;
import com.example.capstone.Model.User;
import com.example.capstone.Repository.GroupSavingAccountRepository;
import com.example.capstone.Repository.PaymentScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class GroupSavingAccountService {


    private final GroupSavingAccountRepository groupSavingAccountRepository;
    private final TransactionService transactionService;
    private final PaymentScheduleService paymentScheduleService;


    public List<GroupSavingAccount> getAllGroupSavingAccounts() {
        return groupSavingAccountRepository.findAll();
    }

    public GroupSavingAccount getGroupSavingAccountById(Integer id) {
        return groupSavingAccountRepository.findGroupSavingAccountById(id);
    }
    public void addGroupSavingAccount(GroupSavingAccount groupSavingAccount) {
        groupSavingAccount= groupSavingAccountRepository.save(groupSavingAccount);
        if(groupSavingAccount.getBalance() > 0){
            Transaction transaction = new Transaction();
            transaction.setGroupSavingAccount(groupSavingAccount);
            transaction.setAmount(groupSavingAccount.getBalance());
            transaction.setTransactionType("Credit");
            transaction.setTransactionDate(groupSavingAccount.getStartDate());
            transaction.setTransactionDetails("Opening Balance");
            transactionService.addTransaction(transaction);
        }
    }

    public void createMonthlyPaymentSchedule(Integer id){
        GroupSavingAccount groupSavingAccount = getGroupSavingAccountById(id);

        if (groupSavingAccount == null) throw new ApiException("Error: Group Saving Account not found");

        List<User> users = groupSavingAccount.getUsers();



        LocalDate todayDate = LocalDate.now();
        double monthlyPayment = 0;

        for (User user : users){
            PaymentSchedule paymentSchedule = new PaymentSchedule();
            if (paymentScheduleService.getPaymentScheduleByUserIdAndPaymentTypeAndMonthAndYear(user.getId() ,
                    "monthlyPayment",(short) todayDate.getMonthValue(),todayDate.getYear()) != null) continue;

            if (user.getGroupSavingAccount() == null) continue;

            monthlyPayment = user.getGroupSavingAccount().getAccountSetting().getMonthlyPayment();

            if (monthlyPayment == 0 ) continue;

            paymentSchedule.setPaymentType("monthlyPayment");
            paymentSchedule.setGroupSavingAccount(groupSavingAccount);
            paymentSchedule.setScheduleDate(LocalDate.of(todayDate.getYear(),todayDate.getMonth(),1));
            paymentSchedule.setAmount(monthlyPayment);
            paymentSchedule.setStatus("not paid");
            paymentSchedule.setUser(user);

            paymentScheduleService.addPaymentSchedule(paymentSchedule);

        }
    }
    public void updateBalance(Integer id) {
        GroupSavingAccount groupSavingAccount = groupSavingAccountRepository.findGroupSavingAccountById(id);

        if (groupSavingAccount == null) {
            throw new ApiException("Error: GroupSavingAccount not found");
        }


        List<Transaction> transactions = groupSavingAccount.getTransactions();

        if (transactions.isEmpty()) {
            throw new ApiException("Error: There are no transactions in the account");
        }

        double credit = transactions.stream()
                .filter(transaction -> "Credit".equalsIgnoreCase(transaction.getTransactionType()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double debit = transactions.stream()
                .filter(transaction -> "Debit".equalsIgnoreCase(transaction.getTransactionType()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        groupSavingAccount.setBalance(credit - debit);


        groupSavingAccountRepository.save(groupSavingAccount);
    }
    public void updateGroupSavingAccount(Integer id, GroupSavingAccount groupSavingAccount) {
        if (groupSavingAccountRepository.findGroupSavingAccountById(id) == null) throw new ApiException("Error: GroupSavingAccount not found");

        groupSavingAccount.setId(id);
        groupSavingAccountRepository.save(groupSavingAccount);
    }
    public void deleteGroupSavingAccount(Integer id) {
        if (groupSavingAccountRepository.findGroupSavingAccountById(id) == null) throw new ApiException("Error: GroupSavingAccount not found");

        groupSavingAccountRepository.deleteById(id);
    }


}
