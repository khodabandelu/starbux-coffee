package com.example.starbux.cart.domain;

import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public class CartItem {

    private String productId;
    private String toppingId;
    private double price;

    public CartItem(String productId, String toppingId, double price) {
        if (!StringUtils.hasText(productId)) {
            throw new IllegalStateException("Product must be defined!");
        }
        if (price<=0) {
            throw new IllegalStateException("Product price be greater than zero!");
        }
        this.toppingId = toppingId;
        this.price = price;
    }

}
