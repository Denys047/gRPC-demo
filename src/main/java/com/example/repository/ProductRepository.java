package com.example.repository;

import com.example.model.Product;

import java.util.*;

public class ProductRepository {

    private static final Map<Integer, Product> DB = new HashMap<>();

    static {
        DB.put(1, new Product(1, "Laptop", "A high-performance laptop"));
        DB.put(2, new Product(2, "Smartphone", "Latest model smartphone"));
        DB.put(3, new Product(3, "Tablet", "Portable and powerful tablet"));
        DB.put(4, new Product(4, "Smartwatch", "Stylish smartwatch with fitness tracking"));
        DB.put(5, new Product(5, "Headphones", "Noise-cancelling headphones"));
        DB.put(6, new Product(6, "Laptop", "Affordable laptop for students"));
        DB.put(7, new Product(7, "Smartphone", "Budget-friendly smartphone"));
        DB.put(8, new Product(8, "Camera", "Professional DSLR camera"));
        DB.put(9, new Product(9, "Printer", "Compact wireless printer"));
        DB.put(10, new Product(10, "Monitor", "4K UHD monitor"));
        DB.put(11, new Product(11, "Headphones", "Wireless over-ear headphones"));
        DB.put(12, new Product(12, "Tablet", "High-resolution tablet for artists"));
        DB.put(13, new Product(13, "Smartwatch", "Smartwatch with GPS and health tracking"));
    }

    public Optional<Product> findProductById(int id) {
        return Optional.ofNullable(DB.get(id));
    }

    public List<Product> findAllProductsByName(String name) {
        List<Product> products = new ArrayList<>();
        DB.values().forEach(product -> {
            if (product.getName().equals(name)) {
                products.add(product);
            }
        });
        return products;
    }

}
