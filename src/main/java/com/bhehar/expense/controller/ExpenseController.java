package com.bhehar.expense.controller;

import com.bhehar.expense.entity.Expense;
import com.bhehar.expense.entity.User;
import com.bhehar.expense.repository.ExpenseRepository;
import com.bhehar.expense.repository.UserRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepo;
    @Autowired
    private UserRepository userRepo;

    @GetMapping
    public List<Expense> getExpenses(@RequestParam(required = false) String employeeId) {
        if (employeeId != null) {
            return expenseRepo.findByEmployeeId(employeeId);
        }
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
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense exp) {

        Optional<Expense> found = expenseRepo.findById(id);
        if (found.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Expense dbExp = found.get();

        dbExp.setUpdatedAt(LocalDateTime.now());
        dbExp.setAmount(exp.getAmount());
        dbExp.setCategory(exp.getCategory());
        dbExp.setDescription(exp.getDescription());

        Expense updated = expenseRepo.save(dbExp);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Expense> updateExpenseStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {

        Optional<Expense> found = expenseRepo.findById(id);
        if (found.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Expense dbExp = found.get();

        // check if user has ADMIN role
        User actor = this.userRepo.findByEmployeeId(body.get("employeeId"));
        if (!actor.getRole().equals(User.UserRole.ADMIN)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Expense.Status newStatus = Expense.Status.valueOf(body.get("status"));
        dbExp.setStatus(newStatus);
        dbExp.setUpdatedAt(LocalDateTime.now());

        Expense updated = expenseRepo.save(dbExp);
        return ResponseEntity.ok(updated);

    }
}
