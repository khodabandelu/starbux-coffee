package com.khodabandelu.starbux.cart.api.queries;

import com.khodabandelu.cqrs.core.queries.AggregateQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindTotalPriceAllCartsByCustomerQuery extends AggregateQuery {
    private String customer;
}
