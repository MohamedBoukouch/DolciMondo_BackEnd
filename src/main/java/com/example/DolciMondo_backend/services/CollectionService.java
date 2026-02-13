package com.example.DolciMondo_backend.services;

import com.example.DolciMondo_backend.models.Collection;
import com.example.DolciMondo_backend.repository.CollectionRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;

    public CollectionService(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    // Get all collections
    public List<Collection> getAllCollections() {
        return collectionRepository.findAll();
    }

    // Get collection by ID
    public Optional<Collection> getCollectionById(Long id) {
        return collectionRepository.findById(id);
    }

    // Create new collection
    public Collection createCollection(Collection collection) {
        return collectionRepository.save(collection);
    }

    // Update collection
    public Collection updateCollection(Long id, Collection updatedCollection) {
        return collectionRepository.findById(id)
                .map(collection -> {
                    collection.setName(updatedCollection.getName());
                    collection.setDescription(updatedCollection.getDescription());
                    collection.setImageUrl(updatedCollection.getImageUrl()); // handle image URL
                    return collectionRepository.save(collection);
                })
                .orElseThrow(() -> new RuntimeException("Collection not found with id " + id));
    }

    // Delete collection
    public void deleteCollection(Long id) {
        collectionRepository.deleteById(id);
    }
}
