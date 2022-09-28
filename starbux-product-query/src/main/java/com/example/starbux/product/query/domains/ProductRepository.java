package com.example.starbux.product.query.domains;

import com.khodabandelu.cqrs.core.domain.BaseEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CassandraRepository<Product, String> {

    Optional<Product> findByName(String name);
    Optional<Product> findByCategoryType(String categoryType);

    List<BaseEntity> findByPriceGreaterThan(double balance);

    List<BaseEntity> findByPriceLessThan(double balance);
}