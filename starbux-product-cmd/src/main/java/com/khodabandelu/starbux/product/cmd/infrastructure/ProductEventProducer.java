package com.khodabandelu.starbux.product.cmd.infrastructure;

import com.khodabandelu.cqrs.core.events.BaseEvent;
import com.khodabandelu.cqrs.core.producers.EventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductEventProducer implements EventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void produce(String topic, BaseEvent event) {
        this.kafkaTemplate.send(topic, event);
    }
}