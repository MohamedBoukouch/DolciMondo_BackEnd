package com.example.DolciMondo_backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @ManyToOne
    @JoinColumn(name = "collection_id", nullable = false)
    private Collection collection; 

    @Column(length = 1000)
    private String description;

    private Double oldPrice;
    private Double newPrice;
    private Integer stock;
    private String image;
    private Boolean status;

}