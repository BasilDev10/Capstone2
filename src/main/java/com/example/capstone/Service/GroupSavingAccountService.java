package com.example.capstone.Service;

import com.example.capstone.ApiResponse.ApiException;
import com.example.capstone.Model.*;
import com.example.capstone.Repository.AccountSettingRepository;
import com.example.capstone.Repository.GroupSavingAccountRepository;
import com.example.capstone.Repository.PaymentScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class GroupSavingAccountService {


    private final GroupSavingAccountRepository groupSavingAccountRepository;
    private final TransactionService transactionService;
    private final PaymentScheduleService paymentScheduleService;
    private final UserService userService;
    private final AccountSettingRepository accountSettingRepository;
    private final AccountSettingService accountSettingService;


    public List<GroupSavingAccount> getAllGroupSavingAccounts() {
        return groupSavingAccountRepository.findAll();
    }

    public GroupSavingAccount getGroupSavingAccountById(Integer id) {
        return groupSavingAccountRepository.findGroupSavingAccountById(id);
    }
    public void addGroupSavingAccount(GroupSavingAccount groupSavingAccount) {
        AccountSetting accountSetting = new AccountSetting();
        accountSetting.setName(groupSavingAccount.getName());
        accountSetting.setApplyLoan(true);
        accountSetting.setApplyLoanRequest(true);
        accountSetting.setMonthlyPayment(500.0);
        accountSetting.setPercentageLonaAllowed(50);
        accountSetting = accountSettingRepository.save(accountSetting);
        groupSavingAccount.setAccountSettingId(accountSetting.getId());

        groupSavingAccount= groupSavingAccountRepository.save(groupSavingAccount);

        if(groupSavingAccount.getBalance() > 0){
            Transaction transaction = new Transaction();
            transaction.setGroupSavingAccountId(groupSavingAccount.getId());
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

        List<User> users = userService.getAllUsersByGroupSavingAccountId(groupSavingAccount.getId());



        LocalDate todayDate = LocalDate.now();
        double monthlyPayment = 0;

        for (User user : users){
            PaymentSchedule paymentSchedule = new PaymentSchedule();
            if (paymentScheduleService.getPaymentScheduleByUserIdAndPaymentTypeAndMonthAndYear(user.getId() ,
                    "monthlyPayment","system", todayDate.getMonthValue(),todayDate.getYear()) != null) continue;

            if (user.getGroupSavingAccountId() == null) continue;

            AccountSetting accountSetting = accountSettingService.getAccountSettingById(groupSavingAccount.getAccountSettingId());

            monthlyPayment = accountSetting.getMonthlyPayment();

            if (monthlyPayment == 0 ) continue;

            paymentSchedule.setPaymentType("monthlyPayment");
            paymentSchedule.setScheduleCreatedType("system");
            paymentSchedule.setPaidAmount(0.0);
            paymentSchedule.setGroupSavingAccountId(groupSavingAccount.getId());
            paymentSchedule.setScheduleDate(LocalDate.of(todayDate.getYear(),todayDate.getMonth(),1));
            paymentSchedule.setAmount(monthlyPayment);
            paymentSchedule.setStatus("not paid");
            paymentSchedule.setUserId(user.getId());

            paymentScheduleService.addPaymentSchedule(paymentSchedule);

        }
    }
    public void updateBalance(Integer id) {
        GroupSavingAccount groupSavingAccount = groupSavingAccountRepository.findGroupSavingAccountById(id);

        if (groupSavingAccount == null) {
            throw new ApiException("Error: GroupSavingAccount not found");
        }


        List<Transaction> transactions = transactionService.getTransactionsByGroupSavingAccountId(groupSavingAccount.getId());

        if (transactions.isEmpty()) {
            groupSavingAccount.setBalance(0.0);
            groupSavingAccountRepository.save(groupSavingAccount);
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

    public void updatePaymentSchedule(Integer id) {
        GroupSavingAccount groupSavingAccount = groupSavingAccountRepository.findGroupSavingAccountById(id);
        if (groupSavingAccount == null) throw new ApiException("Error: GroupSavingAccount not found");
        List<PaymentSchedule> paymentSchedules = paymentScheduleService.getByGroupSavingAccountId(id);
        if (paymentSchedules.isEmpty()) return;

        List<User> users = userService.getAllUsersByGroupSavingAccountId(groupSavingAccount.getId());

        if (users.isEmpty()) return;

        List<Transaction> transactions = transactionService.getTransactionsByGroupSavingAccountId(groupSavingAccount.getId());


        for (User user : users){


            List<PaymentSchedule> userPaymentSchedule = paymentSchedules.stream()
                    .filter(paymentSchedule -> Objects.equals(paymentSchedule.getUserId(), user.getId()))
                    .sorted(Comparator.comparing(PaymentSchedule::getScheduleDate))
                    .toList();

            double userPaidAmount =  transactions.stream().
                    filter(transaction -> Objects.equals(transaction.getUserId(), user.getId()) &&  "Credit".equalsIgnoreCase(transaction.getTransactionType()) )
                    .mapToDouble(Transaction::getAmount)
                    .sum();

            user.setTotalPaidAmount(userPaidAmount);

            userService.updateUser(user.getId(), user);

            for (PaymentSchedule paymentSchedule : userPaymentSchedule){
                if(userPaidAmount == 0){
                    paymentSchedule.setStatus("not paid");
                    paymentSchedule.setPaidAmount(0.0);
                    paymentScheduleService.updatePaymentSchedule(paymentSchedule.getId(),paymentSchedule);
                }else if (userPaidAmount >= paymentSchedule.getAmount() ){
                    paymentSchedule.setStatus("paid");
                    paymentSchedule.setPaidAmount(paymentSchedule.getAmount());
                    paymentScheduleService.updatePaymentSchedule(paymentSchedule.getId(),paymentSchedule);
                    userPaidAmount -= paymentSchedule.getAmount();
                }else if (userPaidAmount < paymentSchedule.getAmount() ){
                    paymentSchedule.setStatus("partial paid");
                    paymentSchedule.setPaidAmount(userPaidAmount);
                    paymentScheduleService.updatePaymentSchedule(paymentSchedule.getId(),paymentSchedule);
                    userPaidAmount = 0;
                }
            }

            double targetMemberTotalPayment = (paymentSchedules.stream()
                    .filter(paymentSchedule -> paymentSchedule.getPaymentType().equalsIgnoreCase("monthlyPayment"))
                    .mapToDouble(PaymentSchedule::getAmount)
                    .sum())/users.size();
            groupSavingAccount.setTargetMemberTotalPayment(targetMemberTotalPayment);

            double targetAccountTotalPayment = paymentSchedules.stream()
                    .mapToDouble(PaymentSchedule::getAmount)
                    .sum();
            groupSavingAccount.setTargetAccountTotalPayment(targetAccountTotalPayment);

            double overdueAmount = paymentScheduleService.getByGroupSavingAccountId(id)
                    .stream()
                    .filter(paymentSchedule ->
                            paymentSchedule.getStatus().equalsIgnoreCase("not paid") ||
                                    paymentSchedule.getStatus().equalsIgnoreCase("partial paid")
                    )
                    .mapToDouble(paymentSchedule ->
                            paymentSchedule.getAmount() - paymentSchedule.getPaidAmount()
                    )
                    .sum();

            groupSavingAccount.setOverdueAmount(overdueAmount);
            groupSavingAccountRepository.save(groupSavingAccount);


        }

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
