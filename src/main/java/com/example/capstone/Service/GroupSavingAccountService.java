package com.example.capstone.Service;

import com.example.capstone.ApiResponse.ApiException;
import com.example.capstone.Model.GroupSavingAccount;
import com.example.capstone.Model.Transaction;
import com.example.capstone.Repository.GroupSavingAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class GroupSavingAccountService {


    private final GroupSavingAccountRepository groupSavingAccountRepository;
    private final TransactionService transactionService;


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

    public void updateBalance(Integer id) {
        GroupSavingAccount groupSavingAccount = groupSavingAccountRepository.findGroupSavingAccountById(id);

        if (groupSavingAccount == null) {
            throw new ApiException("Error: GroupSavingAccount not found");
        }

        // Fetch transactions and ensure they are updated in place
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

        // Save only when the collection is consistent
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
