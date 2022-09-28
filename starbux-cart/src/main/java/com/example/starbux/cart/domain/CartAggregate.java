package com.example.starbux.cart.domain;

import com.example.starbux.cart.api.commands.OpenCartCommand;
import com.example.starbux.cart.dto.Product;
import com.example.starbux.cart.entities.CartEntity;
import com.example.starbux.cart.entities.CartItemEntity;
import com.khodabandelu.cqrs.core.domain.AggregateRoot;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
public class CartAggregate extends AggregateRoot {
    private Boolean confirmed;
    private String customer;
    private double discountPrice;
    private double originalPrice;
    private double totalPrice;
    private final List<CartItem> items;

    public CartAggregate(OpenCartCommand command, Product firstProduct) {
        this.id = command.getId();
        this.customer = command.getCustomer();
        this.confirmed = false;
        this.items = new ArrayList<>();
        addItem(firstProduct, null);
    }

    public CartAggregate(CartEntity entity) {
        if (entity == null) {
            throw new IllegalStateException("Cart not found by this id!");
        }
        this.id = entity.getId();
        this.confirmed = entity.getConfirmed();
        this.customer = entity.getCustomer();
        this.originalPrice = entity.getOriginalPrice();
        this.items = new ArrayList<>();
        if (entity.getItems() != null && entity.getItems().size() > 0) {
            entity.getItems().forEach(item -> {
                this.items.add(new CartItem(item.getProductId(), item.getToppingId(), item.getPrice()));
                this.originalPrice += item.getPrice();
            });
        }
        validateItems();
        calculatePrices();
    }

    private void validateItems() {
        this.items.stream()
                .filter(item -> item.getToppingId() != null)
                .forEach(topping -> {
                    this.items.stream()
                            .filter(i -> i.getProductId().equals(topping.getProductId()))
                            .findAny()
                            .orElseThrow(() -> {
                                throw new IllegalStateException("This topping must be related to a product" + topping.getToppingId());
                                // we can add product id manually to our items instead of throws exceptions
                            });
                });
    }

    public void addItem(Product product, Product topping) {
        if (product == null || !StringUtils.hasText(product.getId())) {
            throw new IllegalStateException("Product must be filled!");
        }
        if (product.getCategoryType().equals("product")) {
            throw new IllegalStateException("Product must be use product category type!");
        }
        if (topping != null && topping.getCategoryType().equals("topping")) {
            throw new IllegalStateException("Topping must be use topping category type!");
        }
        CartItem item;
        if (topping != null && StringUtils.hasText(topping.getId())) {
            item = new CartItem(product.getId(), topping.getId(), product.getPrice());
        } else {
            item = new CartItem(product.getId(), null, product.getPrice());
        }
        this.items.add(item);
        this.originalPrice += product.getPrice();
        calculatePrices();
    }

    public void removeItem(Product product) {
        if (product == null || !StringUtils.hasText(product.getId())) {
            throw new IllegalStateException("Product must be filled!");
        }
        if (product.getCategoryType().equals("product")) {
            this.items.removeIf(item -> item.getProductId().equals(product.getId()));
        } else if (product.getCategoryType().equals("topping")) {
            this.items.removeIf(item -> item.getToppingId().equals(product.getId()));
        }
        this.originalPrice = this.items.stream().mapToDouble(CartItem::getPrice).sum();
        calculatePrices();
    }

    public void confirmCart() {
        if (!this.confirmed) {
            throw new IllegalStateException("The cart has already been confirmed!");
        }
        this.confirmed = true;
    }

    private void calculatePrices() {
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

    public CartEntity getEntity() {
        var cartItems = new ArrayList<CartItemEntity>();
        this.items.forEach(item -> cartItems.add(new CartItemEntity(item.getProductId(), item.getToppingId(), item.getPrice())));
        return CartEntity.builder()
                .id(this.id)
                .customer(this.customer)
                .confirmed(this.confirmed)
//                .originalPrice(this.originalPrice)
//                .discountPrice(this.discountPrice)
//                .totalPrice(this.totalPrice)
                .items(cartItems)
                .build();
    }

}
