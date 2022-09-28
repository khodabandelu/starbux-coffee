package com.khodabandelu.cqrs.core.producers;

import com.khodabandelu.cqrs.core.events.BaseEvent;

public interface EventProducer {
    void produce(String topic, BaseEvent event);
}
