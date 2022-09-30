package com.khodabandelu.starbux.product.query.domains;

import com.khodabandelu.cqrs.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;

@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {
    @PrimaryKey
    private String id;
    private String name;
    private String categoryType;
    private Date creationDate;
    private double price;
}
