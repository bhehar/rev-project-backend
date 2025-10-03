package com.bhehar.expense.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bhehar.expense.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

}
