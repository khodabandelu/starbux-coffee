package com.example.starbux.cart.services;

import com.example.starbux.cart.dto.Product;

public interface ProductService {
    Product findById(String id);
}
