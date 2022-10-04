package com.khodabandelu.starbux.cart.dao;

import com.khodabandelu.starbux.cart.entities.CartEntity;
import com.khodabandelu.starbux.cart.entities.CartItemEntity;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<CartEntity, String> {
    List<CartEntity> findByCustomer(String customer);

    List<CartEntity> findByCustomerAndConfirmed(String customer, boolean confirmed);

    Optional<CartEntity> findByCustomerAndConfirmedFalse(String customer);

    Optional<CartEntity> findFirstByCustomerOrderByCreatedDateDesc(String customer);

    Optional<CartEntity> findFirstByCustomerAndConfirmedOrderByCreatedDateDesc(String customer, boolean confirmed);

    @Query("select sum(totalPrice) from CartEntity where customer like ?1")
    Optional<Double> sumTotalAmountOfAllCartsByCustomer(String customer);

    @Aggregation("")
    List<CartItemEntity> mostUsedTopping();
}
