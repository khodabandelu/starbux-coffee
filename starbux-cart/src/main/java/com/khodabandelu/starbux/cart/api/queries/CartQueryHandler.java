package com.khodabandelu.starbux.cart.api.queries;

import com.khodabandelu.cqrs.core.domain.BaseEntity;
import com.khodabandelu.starbux.cart.api.dto.MostUsedProductDto;
import com.khodabandelu.starbux.cart.dao.CartRepository;
import com.khodabandelu.starbux.cart.entities.CartEntity;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
@RequiredArgsConstructor
public class CartQueryHandler implements QueryHandler {
    private final CartRepository cartRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public List<BaseEntity> handle(FindAllCartsQuery query) {
        Iterable<CartEntity> carts = cartRepository.findAll();
        List<BaseEntity> cartList = new ArrayList<>();
        carts.forEach(cartList::add);
        return cartList;
    }

    @Override
    public List<BaseEntity> handle(FindCartByIdQuery query) {
        var cart = cartRepository.findById(query.getId());
        if (cart.isEmpty()) {
            return null;
        }
        List<BaseEntity> cartList = new ArrayList<>();
        cartList.add(cart.get());
        return cartList;
    }

    @Override
    public List<BaseEntity> handle(FindCartByCustomerQuery query) {
        Iterable<CartEntity> carts = cartRepository.findByCustomer(query.getCustomer());
        List<BaseEntity> cartList = new ArrayList<>();
        carts.forEach(cartList::add);
        return cartList;
    }

    @Override
    public List<BaseEntity> handle(FindLastCartByCustomerQuery query) {
        var cart = cartRepository.findFirstByCustomerOrderByCreatedDateDesc(query.getCustomer());
        if (cart.isEmpty()) {
            return null;
        }
        List<BaseEntity> cartList = new ArrayList<>();
        cartList.add(cart.get());
        return cartList;
    }

    @Override
    public List<BaseEntity> handle(FindLastConfimredCartByCustomerQuery query) {
        var cart = cartRepository.findFirstByCustomerAndConfirmedOrderByCreatedDateDesc(query.getCustomer(),true);
        if (cart.isEmpty()) {
            return null;
        }
        List<BaseEntity> cartList = new ArrayList<>();
        cartList.add(cart.get());
        return cartList;
    }

    @Override
    public List<BaseEntity> handle(FindCurrentCartByCustomerQuery query) {
        var cart = cartRepository.findByCustomerAndConfirmedFalse(query.getCustomer());
        if (cart.isEmpty()) {
            return null;
        }
        List<BaseEntity> cartList = new ArrayList<>();
        cartList.add(cart.get());
        return cartList;
    }

    @Override
    public Double handle(FindTotalPriceAllCartsByCustomerQuery query) {
        GroupOperation sumTotalPriceGroup = group("customer")
                .sum("totalPrice").as("sumTotalPrice");
        MatchOperation filterCustomer = match(new Criteria("customer").is(query.getCustomer()));
        Aggregation aggregation = Aggregation.newAggregation(filterCustomer, sumTotalPriceGroup);

        AggregationResults<Document> result = mongoTemplate.aggregate(
                aggregation, "cart", Document.class);
        if (result.getUniqueMappedResult()==null){
            return null;
        }
        return result.getUniqueMappedResult().getDouble("sumTotalPrice");
    }

    @Override
    public List<BaseEntity> handle(FindMostUsedToppingQuery query) {
        GroupOperation sumTotalQtyGroup = group("productId")
                .sum("qty").as("sumTotalQty");
        SortOperation sortByTotalQtyDesc = sort(Sort.by(Sort.Direction.DESC, "sumTotalQty"));
        Aggregation aggregation = Aggregation.newAggregation(sumTotalQtyGroup, sortByTotalQtyDesc);

        AggregationResults<MostUsedProductDto> result = mongoTemplate.aggregate(
                aggregation, "cartItem", MostUsedProductDto.class);
        Iterable<MostUsedProductDto> mostUsedProducts = result.getMappedResults();
        List<BaseEntity> MostUsedProductList = new ArrayList<>();
        mostUsedProducts.forEach(MostUsedProductList::add);

        return MostUsedProductList;
    }


}
