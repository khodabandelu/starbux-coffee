package com.khodabandelu.starbux.cart.dto;

import com.khodabandelu.cqrs.core.domain.BaseEntity;
import com.khodabandelu.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {
    private String id;
    private String name;
    private String categoryType;
    private double price;
}
