package com.example.DolciMondo_backend.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.DolciMondo_backend.dtos.user.UserRequest;
import com.example.DolciMondo_backend.models.User;
import com.example.DolciMondo_backend.models.enums.Role;
import com.example.DolciMondo_backend.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ---------------- LOGIN ----------------
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return "Login successful for " + user.getEmail();
    }

    // ---------------- ADD USER ----------------
    public User addUser(UserRequest request, Long supervisorId) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getNom(),
                request.getPrenom(),
                request.getRole(),
                supervisorId                // null if supervisor
        );

        return userRepository.save(user);
    }

    // ---------------- UPDATE USER ----------------
    public User updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return userRepository.save(user);
    }

    // ---------------- DELETE USER ----------------
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    // ---------------- LIST USERS ----------------
    public List<User> getAllEmployees() {
        return userRepository.findAll()
                .stream()
                .filter(u -> u.getRole() == Role.EMPLOYEE)
                .collect(Collectors.toList());
    }
    

    public List<User> getAllSupervisors() {
        return userRepository.findAll()
                .stream()
                .filter(u -> u.getRole() == Role.SUPERVISEUR)
                .collect(Collectors.toList());
    }

    // ---------------- FIND BY EMAIL ----------------
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
