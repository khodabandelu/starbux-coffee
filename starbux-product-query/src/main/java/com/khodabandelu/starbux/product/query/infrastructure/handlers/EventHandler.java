package com.khodabandelu.starbux.product.query.infrastructure.handlers;

import com.khodabandelu.starbux.common.events.ProductCreatedEvent;
import com.khodabandelu.starbux.common.events.ProductDeletedEvent;
import com.khodabandelu.starbux.common.events.ProductInfoUpdatedEvent;

public interface EventHandler {
    void on(ProductCreatedEvent event);

    void on(ProductInfoUpdatedEvent event);

    void on(ProductDeletedEvent event);

}
