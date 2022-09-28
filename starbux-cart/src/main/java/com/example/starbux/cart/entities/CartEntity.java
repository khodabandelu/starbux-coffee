package com.example.starbux.cart.entities;

import com.example.starbux.cart.domain.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "cart")
public class CartEntity {

    @Id
    private String id;
    private String customer;
//    private double discountPrice;
    private double originalPrice;
//    private double totalPrice;
    private Boolean confirmed;
    private List<CartItemEntity> items;

}
