package com.khodabandelu.starbux.product.query.api.queries;

import com.khodabandelu.starbux.product.query.domains.Product;
import com.khodabandelu.starbux.product.query.dao.ProductRepository;
import com.khodabandelu.cqrs.core.domain.BaseEntity;
import com.khodabandelu.starbux.common.dto.EqualityType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductQueryHandler implements QueryHandler {
    private final ProductRepository productRepository;

    @Override
    public List<BaseEntity> handle(FindAllProductsQuery query) {
        Iterable<Product> products = productRepository.findAll();
        List<BaseEntity> productsList = new ArrayList<>();
        products.forEach(productsList::add);
        return productsList;
    }

    @Override
    public List<BaseEntity> handle(FindProductByIdQuery query) {
        var product = productRepository.findById(query.getId());
        if (product.isEmpty()) {
            return null;
        }
        List<BaseEntity> productList = new ArrayList<>();
        productList.add(product.get());
        return productList;
    }

    @Override
    public List<BaseEntity> handle(FindProductByNameQuery query) {
        var product = productRepository.findByName(query.getName());
        if (product.isEmpty()) {
            return null;
        }
        List<BaseEntity> productList = new ArrayList<>();
        productList.add(product.get());
        return productList;
    }

    @Override
    public List<BaseEntity> handle(FindProductByCategoryQuery query) {
        Iterable<Product> products = productRepository.findByCategoryType(query.getCategoryType());
        List<BaseEntity> productsList = new ArrayList<>();
        products.forEach(productsList::add);
        return productsList;
    }

    @Override
    public List<BaseEntity> handle(FindProductByPriceQuery query) {
        List<BaseEntity> productList = query.getEqualityType() == EqualityType.GREATER_THAN ? productRepository.findByPriceGreaterThan(query.getPrice()) : productRepository.findByPriceLessThan(query.getPrice());
        return productList;
    }
}
