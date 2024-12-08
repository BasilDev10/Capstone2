package com.example.capstone.Service;

import com.example.capstone.ApiResponse.ApiException;
import com.example.capstone.Model.BankFile;
import com.example.capstone.Model.GroupSavingAccount;
import com.example.capstone.Model.Transaction;
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

        bankFile.setGroupSavingAccount(groupSavingAccount);

        bankFile.setName(file.getOriginalFilename());
        bankFile.setPath(file.getOriginalFilename());

        BankFile bankFileCreated = bankFileRepository.save(bankFile);
        List<Transaction> transactions = transactionService.getTransactionsFromFile(file ,  bankFileCreated);
        List<Transaction> expansesAndLoan = transactions.stream()
                .filter(transaction -> "Debit".equalsIgnoreCase(transaction.getTransactionType())).toList();
        transactionService.saveBulkTransactions(transactions );
        groupSavingAccountService.updateBalance(groupSavingAccountId);


    }

    public void updateExpensesAndLoan(List<Transaction> expansesAndLoan){

        for (Transaction transaction : expansesAndLoan){
            if (transaction.getId() == null) continue;
            
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
