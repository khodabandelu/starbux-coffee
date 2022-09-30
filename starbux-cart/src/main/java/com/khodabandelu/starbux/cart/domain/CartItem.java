package com.khodabandelu.starbux.cart.domain;

import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public class CartItem {

    private String productId;
    private String productName;

    private String masterProductId;
    private double price;
    private double totalPrice;
    private int qty;

    public CartItem(String productId,String productName, String masterProductId, double price,int qty) {
        if (!StringUtils.hasText(productId)) {
            throw new IllegalStateException("Product must be defined!");
        }
        if (price <= 0) {
            throw new IllegalStateException("Product price be greater than zero!");
        }
        this.masterProductId = masterProductId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.qty = qty;
        calculatePrice();
    }

    public void addQuantity() {
        this.qty += 1;
        this.totalPrice += this.price;
    }

    public void calculatePrice(){
        this.totalPrice += this.price*this.qty;
    }

}
