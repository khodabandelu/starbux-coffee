package com.example.starbux.cart.services;

import com.example.starbux.cart.domain.CartAggregate;

public interface CartService {
    void save(CartAggregate cart);

    CartAggregate getById(String id);
}
