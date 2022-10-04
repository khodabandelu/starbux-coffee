package com.khodabandelu.starbux.product.query.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private String id;

    private String name;

    private String categoryType;

    private Date creationDate;

    private double price;
}
