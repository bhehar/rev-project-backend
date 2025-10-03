package com.bhehar.expense.repository;

import com.bhehar.expense.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
