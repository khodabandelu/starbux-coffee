package com.khodabandelu.starbux.cart.dto;

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
public class ProductResponse extends BaseResponse {
    private List<Product> products;

    public ProductResponse(String message){
        super(message);
    }
}
