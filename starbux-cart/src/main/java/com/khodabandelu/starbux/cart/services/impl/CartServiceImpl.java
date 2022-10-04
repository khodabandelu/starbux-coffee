package com.khodabandelu.starbux.cart.services.impl;

import com.khodabandelu.starbux.cart.domain.CartAggregate;
import com.khodabandelu.starbux.cart.dao.CartRepository;
import com.khodabandelu.starbux.cart.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Override
    public void save(CartAggregate cart) {
        cartRepository.save(cart.getEntity());
    }

    @Override
    public CartAggregate getById(String id){
        var entity =  this.cartRepository.findById(id);
        if (entity.isEmpty()){
            return null;
        }
        return new CartAggregate(entity.get());
    }
    @Override
    public CartAggregate getCurrentCart(String customer){
        var entity =  this.cartRepository.findByCustomerAndConfirmedFalse(customer);
        if (entity.isEmpty()){
            return null;
        }
        return new CartAggregate(entity.get());
    }

}
