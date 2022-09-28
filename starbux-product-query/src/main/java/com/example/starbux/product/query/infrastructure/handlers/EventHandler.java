package com.example.starbux.product.query.infrastructure.handlers;

import com.khodabandelu.starbux.common.events.ProductCreatedEvent;
import com.khodabandelu.starbux.common.events.ProductDeactivatedEvent;
import com.khodabandelu.starbux.common.events.ProductInfoUpdatedEvent;

public interface EventHandler {
    void on(ProductCreatedEvent event);

    void on(ProductInfoUpdatedEvent event);

    void on(ProductDeactivatedEvent event);

}
