package com.example.starbux.cart.domain;

import com.example.starbux.cart.api.commands.OpenCartCommand;
import com.example.starbux.cart.dto.Product;
import com.example.starbux.cart.entities.CartEntity;
import com.example.starbux.cart.entities.CartItemEntity;
import com.khodabandelu.cqrs.core.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@NoArgsConstructor
public class CartAggregate extends AggregateRoot {
    private Boolean confirmed;
    private String customer;
    private double discountPrice;
    private double originalPrice;
    private double totalPrice;
    private List<CartItem> items;

    public CartAggregate(OpenCartCommand command, Product product) {
        this.id = command.getId();
        this.customer = command.getCustomer();
        this.confirmed = false;
        addProduct(product);
    }

    private void validateFirstDrinkProduct(Product drinkProduct) {
        if (drinkProduct == null) {
            throw new IllegalStateException("Cart must have at least drink product!");
        }
        if (drinkProduct.getCategoryType() == null || !drinkProduct.getCategoryType().equals("drink")) {
            throw new IllegalStateException("Cart must have at least one drink product!");
        }
    }

    public void addItem()

    private void addProduct(Product product) {
        if (this.items == null) {
            validateFirstDrinkProduct(product);
            this.items = new ArrayList<>();
        }
        var item = new CartItem(product.getId(), null, product.getPrice());
        this.items.add(item);
        this.originalPrice += product.getPrice();
        calculatePrice();
    }

    private void addTopping(String productId, Product topping) {
        var item = new CartItem(productId, topping.getId(), topping.getPrice());
        this.items.add(item);
        calculatePrice();
    }

    private void calculatePrice() {
        double firstPromotionDiscount = 0;
        double secondPromotionDiscount = 0;
        if (firstPromotion()) {
            firstPromotionDiscount = 0.25 * this.originalPrice;
        }
        if (secondPromotion()) {
            var minPrice = this.items.stream().min(Comparator.comparingDouble(CartItem::getPrice)).get();
            secondPromotionDiscount = minPrice.getPrice();
        }
        if (firstPromotionDiscount > 0 & secondPromotionDiscount > 0) {
            this.discountPrice = Math.min(firstPromotionDiscount, secondPromotionDiscount);
        } else if (firstPromotionDiscount > 0) {
            this.discountPrice = firstPromotionDiscount;
        } else if (secondPromotionDiscount > 0) {
            this.discountPrice = secondPromotionDiscount;
        }
        this.totalPrice = this.originalPrice - this.discountPrice;
    }

    private boolean firstPromotion() {
        return originalPrice > 12;
    }

    private boolean secondPromotion() {
        var items = this.items.stream().filter(item -> item.getToppingId() == null).toList();
        return items.size() >= 3;
    }


    public CartAggregate(CartEntity entity) {
        if (entity==null){
            throw new IllegalStateException("Cart not found by this id!");
        }
        this.id= entity.getId();
        this.confirmed=entity.getConfirmed();
        this.customer= entity.getCustomer();
        this.originalPrice =entity.getOriginalPrice();
        if (entity.getItems()!=null&&entity.getItems().size()>0){
            this.items = new ArrayList<CartItem>();
            entity.getItems().forEach(item -> this.items.add(new CartItem(item.getProductId(), item.getToppingId(), item.getPrice())));
        }
        calculatePrice();
    }
    public CartEntity getEntity() {
        var cartItems = new ArrayList<CartItemEntity>();
        this.items.forEach(item -> cartItems.add(new CartItemEntity(item.getProductId(), item.getToppingId(), item.getPrice())));
        return CartEntity.builder()
                .id(this.id)
                .customer(this.customer)
                .confirmed(this.confirmed)
                .originalPrice(this.originalPrice)
//                .discountPrice(this.discountPrice)
//                .totalPrice(this.totalPrice)
                .items(cartItems)
                .build();
    }

}
