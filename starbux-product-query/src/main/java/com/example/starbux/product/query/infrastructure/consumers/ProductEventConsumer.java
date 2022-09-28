package com.example.starbux.product.query.infrastructure.consumers;

import com.example.starbux.product.query.infrastructure.handlers.EventHandler;
import com.khodabandelu.starbux.common.events.ProductCreatedEvent;
import com.khodabandelu.starbux.common.events.ProductDeactivatedEvent;
import com.khodabandelu.starbux.common.events.ProductInfoUpdatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class ProductEventConsumer implements EventConsumer {

    @Autowired
    private EventHandler eventHandler;

    @KafkaListener(topics = "ProductCreatedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(ProductCreatedEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "ProductInfoUpdatedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(ProductInfoUpdatedEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "ProductDeactivatedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(ProductDeactivatedEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }

}
