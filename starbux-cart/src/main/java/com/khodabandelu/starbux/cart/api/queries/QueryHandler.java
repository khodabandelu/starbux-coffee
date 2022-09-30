package com.khodabandelu.starbux.cart.api.queries;

import com.khodabandelu.cqrs.core.domain.BaseEntity;

import java.util.List;

public interface QueryHandler {
    List<BaseEntity> handle(FindAllCartsQuery query);
    List<BaseEntity> handle(FindCartByIdQuery query);
    List<BaseEntity> handle(FindCartByCustomerQuery query);

    Double handle(FindTotalPriceAllCartsByCustomerQuery query);

    List<BaseEntity> handle(FindMostUsedToppingQuery query);



}
