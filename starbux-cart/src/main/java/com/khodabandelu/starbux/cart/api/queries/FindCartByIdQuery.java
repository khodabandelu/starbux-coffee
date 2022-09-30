package com.khodabandelu.starbux.cart.api.queries;

import com.khodabandelu.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindCartByIdQuery extends BaseQuery {
    private String id;
}
