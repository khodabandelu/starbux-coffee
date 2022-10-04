package com.khodabandelu.starbux.product.query.infrastructure.handlers;

import com.khodabandelu.starbux.common.events.ProductCreatedEvent;
import com.khodabandelu.starbux.common.events.ProductDeletedEvent;
import com.khodabandelu.starbux.common.events.ProductInfoUpdatedEvent;
import com.khodabandelu.starbux.product.query.dao.ProductRepository;
import com.khodabandelu.starbux.product.query.domains.Product;
import com.khodabandelu.starbux.product.query.domains.ProductPrimaryKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductEventHandler implements EventHandler {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public void on(ProductCreatedEvent event) {
        var product = Product.builder()
//                .id(ProductPrimaryKey.builder().id(event.getId()).name(event.getName()).categoryType(event.getCategoryType()).build())
                .id(event.getId())
                .name(event.getName())
                .categoryType(event.getCategoryType())
                .creationDate(event.getCreatedDate())
                .price(event.getPrice())
                .build();
        productRepository.save(product);
    }

    @Override
    public void on(ProductInfoUpdatedEvent event) {
        productRepository.findById(event.getId())
                .ifPresent(product -> {
                    product.setName(event.getName());
                    product.setCategoryType(event.getCategoryType());
                    product.setPrice(event.getPrice());
                    productRepository.save(product);
                });
    }

    @Override
    public void on(ProductDeletedEvent event) {
        productRepository.deleteById(event.getId());
    }
}
