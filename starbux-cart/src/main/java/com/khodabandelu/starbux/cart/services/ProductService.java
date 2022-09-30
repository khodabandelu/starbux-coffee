package com.khodabandelu.starbux.cart.services;

import com.khodabandelu.starbux.cart.dto.Product;

public interface ProductService {
    Product findById(String id);
}
