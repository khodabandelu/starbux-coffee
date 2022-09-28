package com.example.starbux.product.query.infrastructure.handlers;

import com.example.starbux.product.query.domains.Product;
import com.example.starbux.product.query.domains.ProductRepository;
import com.khodabandelu.starbux.common.events.ProductCreatedEvent;
import com.khodabandelu.starbux.common.events.ProductDeactivatedEvent;
import com.khodabandelu.starbux.common.events.ProductInfoUpdatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductEventHandler implements EventHandler {
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public void on(ProductCreatedEvent event) {
        var product = Product.builder()
                .id(event.getId())
                .name(event.getName())
                .creationDate(event.getCreatedDate())
                .categoryType(event.getCategoryType())
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
    public void on(ProductDeactivatedEvent event) {
        productRepository.deleteById(event.getId());
    }
}
