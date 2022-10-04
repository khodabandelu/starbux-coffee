package com.khodabandelu.starbux.cart.api.dto;

import com.khodabandelu.starbux.common.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MostUsedProductResponse extends BaseResponse {
    private List<MostUsedProductDto> products;

    public MostUsedProductResponse(String message) {
        super(message);
    }
}
