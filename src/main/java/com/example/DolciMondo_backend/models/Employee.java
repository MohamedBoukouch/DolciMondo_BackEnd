package com.example.DolciMondo_backend.models;


import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "employees")
public class Employee extends User {

    private String position;

    @ManyToOne
    @JoinColumn(name = "supervisor_id", nullable = false)
    @JsonBackReference
    private Supervisor supervisor;

    public Employee() {}

    public Employee(String name, String email, String password, String position, Supervisor supervisor) {
        super(name, email, password);
        this.position = position;
        this.supervisor = supervisor;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }
}
