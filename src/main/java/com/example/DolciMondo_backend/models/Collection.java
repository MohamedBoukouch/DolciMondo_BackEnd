package com.example.DolciMondo_backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "collections")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(length = 255)
    private String imageUrl;
   
}
