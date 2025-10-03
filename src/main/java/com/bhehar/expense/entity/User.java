package com.bhehar.expense.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

enum UserRole {
    USER,
    ADMIN
}

@Entity
@Data
@Table(name = "app_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String employeeId;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Column(unique = true)
    @Email
    private String email;

    private String jobTitle;

    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
