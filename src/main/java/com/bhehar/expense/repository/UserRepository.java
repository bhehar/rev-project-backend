package com.bhehar.expense.repository;

import com.bhehar.expense.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmployeeId(String employeeId);
    public List<User> findByRole(User.UserRole role);
}
