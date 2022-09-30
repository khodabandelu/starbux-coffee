package com.khodabandelu.starbux.cart.api.dto;

import com.khodabandelu.starbux.common.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CartAggregateResponse extends BaseResponse {
    private Number aggregateValue;

    public CartAggregateResponse(String message) {
        super(message);
    }
}