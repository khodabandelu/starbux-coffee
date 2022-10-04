package com.khodabandelu.starbux.product.query.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PrimaryKeyClass
public class ProductPrimaryKey {
    @PrimaryKeyColumn(ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String id;

    @PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private String name;

    @PrimaryKeyColumn(ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    private String categoryType;

}
