package com.khodabandelu.starbux.cart.domain;

import com.khodabandelu.starbux.cart.api.commands.CreateCartCommand;
import com.khodabandelu.starbux.cart.dto.Product;
import com.khodabandelu.starbux.cart.entities.CartEntity;
import com.khodabandelu.starbux.cart.entities.CartItemEntity;
import com.khodabandelu.cqrs.core.domain.AggregateRoot;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.*;

@Getter
public class CartAggregate extends AggregateRoot {
    private Boolean confirmed;
    private final String customer;
    private final Date createdDate;
    private double discountPrice;
    private double originalPrice;
    private double totalPrice;
    private final List<CartItem> items;

    public CartAggregate(CreateCartCommand command, Product product, List<Product> toppings) {
        this.id = command.getId();
        this.customer = command.getCustomer();
        this.confirmed = false;
        this.createdDate = new Date();
        this.items = new ArrayList<>();

        addItem(product, null);
        toppings.forEach(topping -> addItem(topping, product));
    }

    public CartAggregate(CartEntity entity) {
        if (entity == null) {
            throw new IllegalStateException("Cart not found by this id!");
        }
        this.id = entity.getId();
        this.confirmed = entity.getConfirmed();
        this.customer = entity.getCustomer();
        this.createdDate = entity.getCreatedDate();
        this.originalPrice = entity.getOriginalPrice();
        this.items = new ArrayList<>();
        if (entity.getItems() != null && entity.getItems().size() > 0) {
            entity.getItems().forEach(item -> {
                var cartItem = new CartItem(item.getProductId(), item.getProductName(), item.getMasterProductId(), item.getPrice(), item.getQty());
                this.items.add(cartItem);
                this.originalPrice += cartItem.getTotalPrice();
            });
        }
        calculatePrices();
    }

    public void addItem(Product product, Product masterProduct) {
        if (confirmed){
            throw new IllegalStateException("The cart has already been confirmed!");
        }
        if (product == null || !StringUtils.hasText(product.getId())) {
            throw new IllegalStateException("Product must be filled!");
        }
        String masterProductId = masterProduct == null ? "" : masterProduct.getId() == null ? "" : masterProduct.getId();
        this.items.stream()
                .filter(item -> item.getProductId().equals(product.getId()) && masterProductId.equals(item.getMasterProductId()))
                .findFirst()
                .ifPresent(CartItem::addQuantity);
        var item = new CartItem(product.getId(), product.getName(), masterProductId, product.getPrice(), 1);
        this.items.add(item);
        this.originalPrice += product.getPrice();
        calculatePrices();
    }

    public void removeItem(Product product, boolean withToppings) {
        if (confirmed){
            throw new IllegalStateException("The cart has already been confirmed!");
        }
        if (product == null || !StringUtils.hasText(product.getId())) {
            throw new IllegalStateException("Product must be filled!");
        }
        this.items.removeIf(item -> item.getProductId().equals(product.getId()));
        if (product.getCategoryType().equals("product") && withToppings) {
            this.items.removeIf(item -> item.getMasterProductId().equals(product.getId()));
        }
        this.originalPrice = this.items.stream().mapToDouble(CartItem::getTotalPrice).sum();
        calculatePrices();
    }

    public void confirmCart() {
        if (!this.confirmed) {
            throw new IllegalStateException("The cart has already been confirmed!");
        }
        this.confirmed = true;
    }

    //todo this calculation must be in price module
    private void calculatePrices() {
        double firstPromotionDiscount = 0;
        double secondPromotionDiscount = 0;
        if (firstPromotion()) {
            firstPromotionDiscount = 0.25 * this.originalPrice;
        }
        if (secondPromotion()) {
            Map<String, Double> m = new HashMap<>();
            var minPriceItem = this.items.stream()
                    .filter(item -> StringUtils.hasText(item.getMasterProductId()))
                    .map(item -> {
                        var sumToppings = this.items.stream().filter(i -> item.getMasterProductId().equals(i.getMasterProductId())).mapToDouble(CartItem::getPrice).sum();
                        return item.getPrice() + sumToppings;
                    })
                    .min(Double::compareTo);
//            var minPriceItem = this.items.stream().min(Comparator.comparingDouble(CartItem::getPrice)).orElse(null);
            secondPromotionDiscount = minPriceItem.orElse(0.0);
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
        var items = this.items.stream().filter(item -> item.getProductId() == null).toList();
        return items.size() >= 3;
    }

    public CartEntity getEntity() {
        var cartItems = new ArrayList<CartItemEntity>();
        this.items.forEach(item -> cartItems.add(new CartItemEntity(item.getProductId(), item.getProductName(), item.getMasterProductId(), item.getPrice(), item.getQty())));
        return CartEntity.builder()
                .id(this.id)
                .customer(this.customer)
                .confirmed(this.confirmed)
                .originalPrice(this.originalPrice)
                .discountPrice(this.discountPrice)
                .totalPrice(this.totalPrice)
                .items(cartItems)
                .build();
    }

}
