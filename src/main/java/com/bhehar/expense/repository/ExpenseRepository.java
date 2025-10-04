package com.bhehar.expense.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bhehar.expense.entity.Expense;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    public List<Expense> findByEmployeeId(String employeeId);
}
