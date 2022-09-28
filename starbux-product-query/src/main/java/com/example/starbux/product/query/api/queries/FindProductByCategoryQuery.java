package com.example.starbux.product.query.api.queries;

import com.khodabandelu.cqrs.core.queries.BaseQuery;
import com.khodabandelu.starbux.common.dto.EqualityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindProductByCategoryQuery extends BaseQuery {
    private String categoryType;
}
