package com.khodabandelu.starbux.cart.services;

import com.khodabandelu.starbux.cart.domain.CartAggregate;

public interface CartService {
    void save(CartAggregate cart);

    CartAggregate getById(String id);
}
