package com.example.DolciMondo_backend.models;

import com.example.DolciMondo_backend.models.enums.Role;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Setter
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String nom;
    private String prenom;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role; // SUPERVISEUR or EMPLOYEE


    @Column(nullable = false)
    private String acceptedInv = "waiting"; // default value for employees

    // Optional: link employee to supervisor
    private Long supervisorId;

    // Constructors
    public User() {}

    public User(String email, String password, String nom, String prenom, Role role, Long supervisorId) {
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
        this.supervisorId = supervisorId;
    }
}
