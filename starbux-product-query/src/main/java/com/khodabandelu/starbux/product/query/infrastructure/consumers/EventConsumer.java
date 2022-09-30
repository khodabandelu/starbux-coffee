package com.khodabandelu.starbux.product.query.infrastructure.consumers;

import com.khodabandelu.starbux.common.events.ProductCreatedEvent;
import com.khodabandelu.starbux.common.events.ProductDeletedEvent;
import com.khodabandelu.starbux.common.events.ProductInfoUpdatedEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    void consume(@Payload ProductCreatedEvent event, Acknowledgment ack);

    void consume(@Payload ProductInfoUpdatedEvent event, Acknowledgment ack);

    void consume(@Payload ProductDeletedEvent event, Acknowledgment ack);

}
