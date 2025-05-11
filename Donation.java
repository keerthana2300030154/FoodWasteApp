package com.foodwaste.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String foodDescription;
    private String location;
    private boolean claimed = false;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    private User donor;

    @ManyToOne
    private User claimedBy;
}
