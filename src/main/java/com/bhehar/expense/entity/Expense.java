package com.bhehar.expense.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// enum Status {
//     NEW,
//     PENDING,
//     APPROVED,
//     DENIED,
// }

// enum Category {
//     TRANSPORTATION,
//     FOOD,
//     LODGING,
//     EQUIPMENT,
//     OTHER,
// }

@Entity
@Data
public class Expense {

    public static enum Status {
        NEW,
        PENDING,
        APPROVED,
        DENIED,
    }

    public static enum Category {
        TRANSPORTATION,
        FOOD,
        LODGING,
        EQUIPMENT,
        OTHER,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String employeeId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Category category;

    @NotNull
    private float amount;

    @NotNull
    private String description;

    private String comment;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
