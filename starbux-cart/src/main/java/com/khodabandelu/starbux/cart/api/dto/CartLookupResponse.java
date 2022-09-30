package com.khodabandelu.starbux.cart.api.dto;

import com.khodabandelu.starbux.cart.entities.CartEntity;
import com.khodabandelu.starbux.common.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CartLookupResponse extends BaseResponse {
    private List<CartEntity> carts;

    public CartLookupResponse(String message) {
        super(message);
    }
}