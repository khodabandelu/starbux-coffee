package com.khodabandelu.starbux.product.cmd.api.dto;

import com.khodabandelu.starbux.common.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductResponse extends BaseResponse {
    private String id;

    public CreateProductResponse(String message, String id) {
        super(message);
        this.id = id;
    }
}
