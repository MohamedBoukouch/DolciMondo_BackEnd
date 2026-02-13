package com.example.DolciMondo_backend.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DolciMondo_backend.models.Collection;
import com.example.DolciMondo_backend.models.Product;
import com.example.DolciMondo_backend.repository.CollectionRepository;
import com.example.DolciMondo_backend.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CollectionRepository categoryRepository;

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get single product by id
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // Create a new product
    public Product createProduct(Product product) {
        Collection category = categoryRepository.findById(product.getCollection().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCollection(category);

        return productRepository.save(product);
    }

    // Update product
    public Product updateProduct(Long id, Product productDetails) {
        Product product = getProductById(id);

        product.setNom(productDetails.getNom());
        product.setDescription(productDetails.getDescription());
        product.setOldPrice(productDetails.getOldPrice());
        product.setNewPrice(productDetails.getNewPrice());
        product.setStock(productDetails.getStock());
        product.setImage(productDetails.getImage());
        product.setStatus(productDetails.getStatus());

        // Update category
        Collection category = categoryRepository.findById(productDetails.getCollection().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCollection(category);

        return productRepository.save(product);
    }

    // Delete product
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}
