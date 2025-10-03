package com.bhehar.expense.controller;

import com.bhehar.expense.entity.Expense;
import com.bhehar.expense.repository.ExpenseRepository;
// import com.bhehar.expense.repository.UserRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepo;

    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<Expense> createExpense(@Valid @RequestBody Expense exp) {
        exp.setCreatedAt(LocalDateTime.now());
        exp.setUpdatedAt(LocalDateTime.now());
        exp.setStatus(Expense.Status.NEW);
        Expense saved = expenseRepo.save(exp);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @Valid @RequestBody Expense exp) {
        // if(!expenseRepo.existsById(id)) {
        //     return ResponseEntity.notFound().build();
        // }

        Optional<Expense> found = expenseRepo.findById(id);
        if (found.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Expense dbExp = found.get();
        
        dbExp.setUpdatedAt(LocalDateTime.now());

        // if role_type == user
        // they can update category, amount and description
        dbExp.setAmount(exp.getAmount());
        dbExp.setCategory(exp.getCategory());
        dbExp.setDescription(exp.getDescription());
        // To Do
        // need some logic to set status = pending if user_role == "USER" is making a change
        // if user_role == admin, then we can set status according to action being taken
        Expense updated = expenseRepo.save(dbExp);
        return ResponseEntity.ok(updated);
    }
}
