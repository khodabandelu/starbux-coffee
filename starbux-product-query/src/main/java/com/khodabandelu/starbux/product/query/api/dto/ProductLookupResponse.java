package com.khodabandelu.starbux.product.query.api.dto;

import com.khodabandelu.starbux.product.query.domains.Product;
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
    private List<ProductDto> products;

    public ProductLookupResponse(String message){
        super(message);
    }
}