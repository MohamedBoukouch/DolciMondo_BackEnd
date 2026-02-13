package com.example.DolciMondo_backend.dtos.user;

import com.example.DolciMondo_backend.models.enums.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String email;
    private String password;
    private String nom;
    private String prenom;
    private Role role;
}
