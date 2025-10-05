package com.bhehar.expense.controller;

import com.bhehar.expense.entity.Expense;
import com.bhehar.expense.entity.User;
import com.bhehar.expense.entity.User.UserRole;
import com.bhehar.expense.repository.UserRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers(@RequestParam(required = false) String role) {
        if (role != null) {
            User.UserRole enumRole = UserRole.valueOf(role.toUpperCase());
            return userRepository.findByRole(enumRole);
        }
        return userRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User saved = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody Map<String, String> body) {
        User found = userRepository.findByEmployeeId(body.get("employeeId"));
        if (found == null) {
            return ResponseEntity.notFound().build();
        }

        if (!found.getPassword().equals(body.get("password"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        found.setPassword(null);
        return ResponseEntity.ok().body(found);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid
    // @RequestBody User user) {
    // if (!userRepository.existsById(id)) {
    // return ResponseEntity.notFound().build();
    // }
    // user.setId(id);
    // User updated = userRepository.save(user);
    // return ResponseEntity.ok(updated);
    // }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
