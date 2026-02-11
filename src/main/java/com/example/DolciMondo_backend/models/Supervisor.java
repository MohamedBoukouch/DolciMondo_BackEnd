package com.example.DolciMondo_backend.models;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "supervisors")
public class Supervisor extends User {

    @OneToMany(mappedBy = "supervisor", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Employee> employees;

    public Supervisor() {}

    public Supervisor(String name, String email, String password) {
        super(name, email, password);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
