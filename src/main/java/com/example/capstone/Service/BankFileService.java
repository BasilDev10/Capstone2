package com.example.capstone.Service;

import com.example.capstone.ApiResponse.ApiException;
import com.example.capstone.Model.*;
import com.example.capstone.Repository.BankFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BankFileService {

    private final BankFileRepository bankFileRepository;
    private final TransactionService transactionService;
    private final GroupSavingAccountService groupSavingAccountService;
    private final ExpensesService expensesService;
    private final LoanService loanService;

    public List<BankFile> getAllBankFiles() {
        return bankFileRepository.findAll();
    }

    public BankFile getBankFileById(Integer id) {
        return bankFileRepository.findBankFileById(id);
    }

    public void addBankFile(MultipartFile file , Integer groupSavingAccountId) {
        BankFile bankFile = new BankFile();

        GroupSavingAccount groupSavingAccount = groupSavingAccountService.getGroupSavingAccountById(groupSavingAccountId);
        if (groupSavingAccount == null) throw new ApiException("Error: GroupSavingAccount not found");

        bankFile.setGroupSavingAccountId(groupSavingAccount.getId());

        bankFile.setName(file.getOriginalFilename());
        bankFile.setPath(file.getOriginalFilename());

        BankFile bankFileCreated = bankFileRepository.save(bankFile);
        List<Transaction> transactions = transactionService.getTransactionsFromFile(file ,  bankFileCreated , groupSavingAccount);
        transactions = transactionService.saveBulkTransactions(transactions );
        List<Transaction> expansesAndLoan = transactions.stream()
                .filter(transaction -> "Debit".equalsIgnoreCase(transaction.getTransactionType())).toList();
        groupSavingAccountService.updateBalance(groupSavingAccountId);
        updateExpensesAndLoan(expansesAndLoan);


    }

    public void updateExpensesAndLoan(List<Transaction> expansesAndLoan){

        for (Transaction transaction : expansesAndLoan){
            if(transaction.getUserId() == null)continue;

            Expenses expenses = expensesService.getExpensesByUserIdAndGroupSavingAccountIdAndExpensesDateAndAmount(transaction.getUserId(),
                    transaction.getGroupSavingAccountId(),
                    transaction.getTransactionDate(),transaction.getAmount());

            if (expenses != null){
                expenses.setTransactionId(transaction.getId());
                expensesService.updateExpenses(expenses.getId(),expenses);
                continue;
            }

            Loan loan =loanService.getLoanByUserIdAndGroupSavingAccountIdAndLoanDateAndAmount(transaction.getUserId(),
                    transaction.getGroupSavingAccountId(),
                    transaction.getTransactionDate(),transaction.getAmount());
            if (loan != null) {
                loan.setTransactionId(transaction.getId());
                loanService.updateLoan(loan.getId(),loan);
                continue;
            }

            Expenses newExpenses = new Expenses();

            newExpenses.setUserId(transaction.getUserId());
            newExpenses.setGroupSavingAccountId(transaction.getGroupSavingAccountId());
            newExpenses.setTransactionId(transaction.getId());
            newExpenses.setAmount(transaction.getAmount());
            newExpenses.setType(transaction.getTransactionDetails());
            newExpenses.setExpensesDate(transaction.getTransactionDate());
            expensesService.addExpenses(newExpenses);


        }
    }
    public void updateBankFile(Integer id, BankFile bankFile) {
        if (bankFileRepository.findBankFileById(id) == null)
            throw new ApiException("Error: BankFile not found");

        bankFile.setId(id);
        bankFileRepository.save(bankFile);
    }

    public void deleteBankFile(Integer id) {
        if (bankFileRepository.findBankFileById(id) == null)
            throw new ApiException("Error: BankFile not found");

        bankFileRepository.deleteById(id);
    }
}
