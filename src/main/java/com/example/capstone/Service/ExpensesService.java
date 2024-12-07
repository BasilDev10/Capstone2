package com.example.capstone.Service;

import com.example.capstone.ApiResponse.ApiException;
import com.example.capstone.Model.Expenses;
import com.example.capstone.Repository.ExpensesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpensesService {

    private final ExpensesRepository expensesRepository;

    public List<Expenses> getAllExpenses() {
        return expensesRepository.findAll();
    }

    public Expenses getExpensesById(Integer id) {
        Expenses expenses = expensesRepository.findExpensesById(id);
        if (expenses == null) {
            throw new ApiException("Error: Expenses not found!");
        }
        return expenses;
    }

    public void addExpenses(Expenses expenses) {
        expensesRepository.save(expenses);
    }

    public void updateExpenses(Integer id, Expenses expenses) {
        if (expensesRepository.findExpensesById(id) == null) {
            throw new ApiException("Error: Expenses not found!");
        }
        expenses.setId(id);
        expensesRepository.save(expenses);
    }

    public void deleteExpenses(Integer id) {
        if (expensesRepository.findExpensesById(id) == null) {
            throw new ApiException("Error: Expenses not found!");
        }
        expensesRepository.deleteById(id);
    }
}
