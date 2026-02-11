package com.example.DolciMondo_backend.dtos.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String email;
    private String password;
    private String nom;
    private String prenom;
}
