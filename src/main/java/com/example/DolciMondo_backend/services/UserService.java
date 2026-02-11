package com.example.DolciMondo_backend.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.DolciMondo_backend.dtos.user.UserRequest;
import com.example.DolciMondo_backend.models.Role;
import com.example.DolciMondo_backend.models.User;
import com.example.DolciMondo_backend.repository.UserRepository;

import java.util.List;
import java.util.Optional;

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

    // ---------------- ADD SUPERVISOR ----------------
    public User addSupervisor(UserRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User supervisor = new User();
        supervisor.setEmail(request.getEmail());
        supervisor.setPassword(passwordEncoder.encode(request.getPassword()));
        supervisor.setNom(request.getNom());
        supervisor.setPrenom(request.getPrenom());
        supervisor.setRole(Role.SUPERVISEUR);

        return userRepository.save(supervisor);
    }

    // ---------------- ADD EMPLOYEE ----------------
    public User addEmployee(UserRequest request, String supervisorEmail) {
        User supervisor = userRepository.findByEmail(supervisorEmail)
                .orElseThrow(() -> new RuntimeException("Supervisor not found"));

        if (supervisor.getRole() != Role.SUPERVISEUR) {
            throw new RuntimeException("Only superviseur can add employees");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User employee = new User();
        employee.setEmail(request.getEmail());
        employee.setPassword(passwordEncoder.encode(request.getPassword()));
        employee.setNom(request.getNom());
        employee.setPrenom(request.getPrenom());
        employee.setRole(Role.EMPLOYEE);

        return userRepository.save(employee);
    }

    // ---------------- UPDATE EMPLOYEE ----------------
    public User updateEmployee(Long id, UserRequest request, String supervisorEmail) {
        User supervisor = userRepository.findByEmail(supervisorEmail)
                .orElseThrow(() -> new RuntimeException("Supervisor not found"));

        if (supervisor.getRole() != Role.SUPERVISEUR) {
            throw new RuntimeException("Only superviseur can update employees");
        }

        User employee = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (employee.getRole() != Role.EMPLOYEE) {
            throw new RuntimeException("Cannot update superviseur");
        }

        employee.setNom(request.getNom());
        employee.setPrenom(request.getPrenom());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            employee.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return userRepository.save(employee);
    }

    // ---------------- DELETE EMPLOYEE ----------------
    public void deleteEmployee(Long id, String supervisorEmail) {
        User supervisor = userRepository.findByEmail(supervisorEmail)
                .orElseThrow(() -> new RuntimeException("Supervisor not found"));

        if (supervisor.getRole() != Role.SUPERVISEUR) {
            throw new RuntimeException("Only superviseur can delete employees");
        }

        User employee = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (employee.getRole() != Role.EMPLOYEE) {
            throw new RuntimeException("Cannot delete superviseur");
        }

        userRepository.delete(employee);
    }

    // ---------------- LIST EMPLOYEES ----------------
    public List<User> getAllEmployees() {
        return userRepository.findAll()
                .stream()
                .filter(u -> u.getRole() == Role.EMPLOYEE)
                .toList();
    }

    // ---------------- FIND BY EMAIL ----------------
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}


