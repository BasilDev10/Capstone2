package com.example.capstone.Service;

import com.example.capstone.Model.*;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import com.example.capstone.ApiResponse.ApiException;
import com.example.capstone.Repository.TransactionRepository;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class TransactionService {


    private final TransactionRepository transactionRepository;
    private final UserBankAccountService userBankAccountService;
    private final UserService userService;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactionsByGroupSavingAccountId(Integer groupSavingAccountId){
        return transactionRepository.findTransactionsByGroupSavingAccountId(groupSavingAccountId);
    }

    public Transaction getTransactionById(Integer id) {
        return transactionRepository.findTransactionById(id);
    }

    public void addTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }
    public List<Transaction> saveBulkTransactions(List<Transaction> transactions ) {
        return transactionRepository.saveAll(transactions);
    }
    public void updateTransaction(Integer id, Transaction transaction) {
        if (transactionRepository.findTransactionById(id) == null)
            throw new ApiException("Error: Transaction not found");

        transaction.setId(id);
        transactionRepository.save(transaction);
    }

    public void deleteTransaction(Integer id) {
        if (transactionRepository.findTransactionById(id) == null)
            throw new ApiException("Error: Transaction not found");

        transactionRepository.deleteById(id);
    }

    public List<Transaction> getTransactionsFromFile(MultipartFile file , BankFile bankFile , GroupSavingAccount groupSavingAccount){
        try {

            PDDocument document = PDDocument.load(file.getInputStream());
            PDFTextStripper pdfStripper = new PDFTextStripper();

            ArrayList<String[]> tables = new ArrayList<>();
            int pageCount = document.getNumberOfPages();

            // Marker for the end of the table
            String endOfTable = "hsadflfmslkdmflkdsmfklsmdfklmsdkf";
            pdfStripper.setPageEnd(endOfTable);

            String text = pdfStripper.getText(document);
            document.close();

            for (int i = 0; i < pageCount - 1; i++) {
                // Start and end markers for the table
                String tableStartMarker = "الرصيد دائن مدين تفاصيل العملية التاريخ";
                String tableEndMarker = (i + 2) + endOfTable;

                int startIndex = text.indexOf(tableStartMarker);

                if (startIndex != -1) {
                    text = text.substring(startIndex + tableStartMarker.length());
                }

                int endIndex = 0;
                if (i != pageCount - 2) {
                    endIndex = text.indexOf(tableEndMarker);
                } else {
                    endIndex = text.indexOf("Note ملاحظة") - 5;
                }

                if (startIndex == -1 || endIndex == -1 || startIndex >= endIndex) {
                    return null;
                }
                String tableContent = text.substring(0, endIndex).trim();

                // Split the table content into rows
                String[] rows = tableContent.split("\n");

                tables.add(rows);

                text = text.replace(tableContent, "");
            }

            return processRows(tables,bankFile , groupSavingAccount);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }
    public List<Transaction> processRows(ArrayList<String []> tables, BankFile bankFile , GroupSavingAccount groupSavingAccount) {
        List<Transaction> transactions = new ArrayList<>();


        for (String[] rows : tables) {



            String transactionDetails = "";
            LocalDate transactionDate = null;
            Double amount = null;
            String transactionType = "";

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            Transaction transaction = new Transaction();
            for (int i = 0; i < rows.length; i++) {
                String row = rows[i].trim();

                if (row.contains("0.00 SAR")) {
                    if (!transaction.equals(new Transaction()) && transaction.getAmount() != null) {

                        UserBankAccount userBankAccount = userBankAccountService.getUserBankAccountByAccountNumber(transaction.getTransactionDetails());
                        if(userBankAccount != null){
                            User user = userService.getUserById(userBankAccount.getUserId());
                            transaction.setUserId(user.getId());
                        }

                        transactions.add(transaction);
                        transaction = new Transaction();
                    }


                    String[] parts = row.split("SAR");
                    if (parts.length >= 3) {

                        String debitAmountStr = parts[2].trim().split(" ")[0];
                        String creditAmountStr = parts[1].trim().split(" ")[0];

                        debitAmountStr = debitAmountStr.replace(",", "");
                        creditAmountStr = creditAmountStr.replace(",", "");

                        if (!debitAmountStr.equals("0.00")) {
                            amount = Double.parseDouble(debitAmountStr);
                            transactionType = "Debit";
                        } else if (!creditAmountStr.equals("0.00")) {
                            amount = Double.parseDouble(creditAmountStr);
                            transactionType = "Credit";
                        }
                    }
                } else if (row.matches("\\d{4}/\\d{2}/\\d{2}")) {

                    transactionDate = LocalDate.parse(row, dateFormatter);

                    if(groupSavingAccount.getStartDate().isAfter(transactionDate)) throw new ApiException("Error : you cant upload report have transaction before account start date");

                    transaction.setTransactionType(transactionType);
                    transaction.setTransactionDate(transactionDate);
                    transaction.setAmount(amount);
                    transaction.setBankFileId(bankFile.getId());
                    transaction.setGroupSavingAccountId(bankFile.getGroupSavingAccountId());


                    transactionDetails = "";
                    amount = null;
                    transactionType = "";
                } else {
                    // Collect transaction details
                    transactionDetails += row + " ";
                    transaction.setTransactionDetails(transactionDetails);
                }
                if (i == rows.length - 1) {transactions.add(transaction);}
            }


        }

        return transactions;
    }

}
