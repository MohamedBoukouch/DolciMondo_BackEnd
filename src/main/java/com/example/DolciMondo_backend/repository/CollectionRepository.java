package com.example.DolciMondo_backend.repository;

import com.example.DolciMondo_backend.models.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {
    // Optional: find by name
    boolean existsByName(String name);
}

