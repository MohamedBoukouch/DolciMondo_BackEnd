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

    // ---------------- ADD EMPLOYEE ----------------
    @PostMapping("/employees")
    public User addEmployee(@RequestBody UserRequest request,
                            @RequestParam String supervisorEmail) {
        return userService.addEmployee(request, supervisorEmail);
    }

    // ---------------- ADD SUPERVISOR ----------------
    @PostMapping("/supervisors")
    public User addSupervisor(@RequestBody UserRequest request) {
        return userService.addSupervisor(request);
    }

    // ---------------- UPDATE EMPLOYEE ----------------
    @PutMapping("/employees/{id}")
    public User updateEmployee(@PathVariable Long id,
                               @RequestBody UserRequest request,
                               @RequestParam String supervisorEmail) {
        return userService.updateEmployee(id, request, supervisorEmail);
    }

    // ---------------- DELETE EMPLOYEE ----------------
    @DeleteMapping("/employees/{id}")
    public String deleteEmployee(@PathVariable Long id,
                                 @RequestParam String supervisorEmail) {
        userService.deleteEmployee(id, supervisorEmail);
        return "Employee deleted successfully";
    }

    // ---------------- LIST ALL EMPLOYEES ----------------
    @GetMapping("/employees")
    public List<User> getAllEmployees() {
        return userService.getAllEmployees();
    }

    // ---------------- FIND USER BY EMAIL ----------------
    @GetMapping("/find")
    public User findByEmail(@RequestParam String email) {
        return userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
