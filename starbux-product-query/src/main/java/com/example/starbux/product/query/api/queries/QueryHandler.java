package com.example.starbux.product.query.api.queries;

import com.khodabandelu.cqrs.core.domain.BaseEntity;

import java.util.List;

public interface QueryHandler {
    List<BaseEntity> handle(FindAllProductsQuery query);

    List<BaseEntity> handle(FindProductByIdQuery query);

    List<BaseEntity> handle(FindProductByNameQuery query);

    List<BaseEntity> handle(FindProductByPriceQuery query);
    List<BaseEntity> handle(FindProductByCategoryQuery query);
}
