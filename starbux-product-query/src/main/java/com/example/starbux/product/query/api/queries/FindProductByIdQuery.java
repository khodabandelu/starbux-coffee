package com.example.starbux.product.query.api.queries;

import com.khodabandelu.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindProductByIdQuery extends BaseQuery {
    private String id;
}
