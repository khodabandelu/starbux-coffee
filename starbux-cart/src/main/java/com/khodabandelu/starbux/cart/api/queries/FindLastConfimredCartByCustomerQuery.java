package com.khodabandelu.starbux.cart.api.queries;

import com.khodabandelu.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindLastConfimredCartByCustomerQuery extends BaseQuery {
    private String customer;
}
