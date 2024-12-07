package com.example.capstone.Repository;

import com.example.capstone.Model.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Integer> {

    Expenses findExpensesById(Integer id);
}
