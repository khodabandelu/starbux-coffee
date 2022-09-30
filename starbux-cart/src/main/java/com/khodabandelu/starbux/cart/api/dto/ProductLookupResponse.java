package com.khodabandelu.starbux.cart.api.dto;

import com.khodabandelu.starbux.cart.dto.Product;
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
public class ProductLookupResponse extends BaseResponse {
    private List<Product> products;

    public ProductLookupResponse(String message){
        super(message);
    }
}