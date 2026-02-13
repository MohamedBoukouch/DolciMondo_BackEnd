package com.example.DolciMondo_backend.controllers;

import org.springframework.web.bind.annotation.*;
import com.example.DolciMondo_backend.models.User;
import com.example.DolciMondo_backend.services.UserService;
import com.example.DolciMondo_backend.dtos.user.UserRequest;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ---------------- LOGIN ----------------
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        return userService.login(email, password);
    }

    // ---------------- ADD USER ----------------
    // Role must be either SUPERVISOR or EMPLOYEE
    @PostMapping
    public User addUser(@RequestBody UserRequest request,
                        @RequestParam(required = false) Long supervisorId) {
        return userService.addUser(request, supervisorId);
    }

    // ---------------- UPDATE USER ----------------
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id,
                           @RequestBody UserRequest request) {
        return userService.updateUser(id, request);
    }

    // ---------------- DELETE USER ----------------
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }

    // ---------------- LIST EMPLOYEES ----------------
    @GetMapping("/employees")
    public List<User> getAllEmployees() {
        return userService.getAllEmployees();
    }

    // ---------------- LIST SUPERVISORS ----------------
    @GetMapping("/supervisors")
    public List<User> getAllSupervisors() {
        return userService.getAllSupervisors();
    }

    // ---------------- FIND USER BY EMAIL ----------------
    @GetMapping("/find")
    public User findByEmail(@RequestParam String email) {
        return userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
