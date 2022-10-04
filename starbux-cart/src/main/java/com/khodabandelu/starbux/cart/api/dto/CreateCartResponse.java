package com.khodabandelu.starbux.cart.api.dto;

import com.khodabandelu.starbux.common.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCartResponse extends BaseResponse {
    private String id;

    public CreateCartResponse(String message, String id) {
        super(message);
        this.id = id;
    }
}
