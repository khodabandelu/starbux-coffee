package com.khodabandelu.starbux.cart.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "cartItem")
public class CartItemEntity {
    private String productId;
    private String productName;
    private String masterProductId;
    private double price;
    private int qty;
}
