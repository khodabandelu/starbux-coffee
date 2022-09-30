package com.khodabandelu.starbux.cart.api.dto;

import com.khodabandelu.cqrs.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MostUsedProductDto extends BaseEntity {
    private String productId;
    private int sumTotalQty;
}
