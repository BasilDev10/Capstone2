package com.example.capstone.Service;


import com.example.capstone.Model.Transaction;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class GroupSavingAccountService {


    public List<Transaction> getTransactions(){
        String pdfPath = "C:\\Monthly_202410.pdf"; // Path to your PDF file

        try {
            // Load PDF document
            PDDocument document = PDDocument.load(new File(pdfPath));
            PDFTextStripper pdfStripper = new PDFTextStripper();

            // Extract text from the PDF
            String text = pdfStripper.getText(document);
            document.close();

            // Create a list of Transaction objects
            List<Transaction> transactions = new ArrayList<>();

            // Split the text into rows based on newlines
            String[] rows = text.split("\n");

            // Iterate through rows and parse relevant data
            for (String row : rows) {
                if (row.contains("دائن") || row.contains("مدين")) {
                    Transaction transaction = new Transaction();

                    // Split the row into columns (adjust logic for actual structure)
                    String[] columns = row.split("\\s+"); // Adjust for the PDF format

                    try {
                        // Map columns to Transaction fields
                        // Example logic for column mapping (adjust indices as needed)
                        transaction.setTransactionDate(LocalDate.parse(columns[0])); // Date column
                        transaction.setTransactionDetails(columns[columns.length - 1]); // Details column

                        if (row.contains("دائن")) {
                            transaction.setTransactionType("Credit");
                            transaction.setAmount(Double.parseDouble(columns[1])); // Amount for دائن
                        } else if (row.contains("مدين")) {
                            transaction.setTransactionType("Debit");
                            transaction.setAmount(Double.parseDouble(columns[1])); // Amount for مدين
                        }

                        // Add the transaction to the list
                        transactions.add(transaction);

                    } catch (Exception e) {
                        System.out.println("Error parsing row: " + row);
                        e.printStackTrace();
                    }
                }
            }

            return transactions;


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }

}
