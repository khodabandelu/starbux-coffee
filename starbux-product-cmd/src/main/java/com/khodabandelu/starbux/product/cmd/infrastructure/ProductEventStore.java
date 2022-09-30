package com.khodabandelu.starbux.product.cmd.infrastructure;

import com.khodabandelu.cqrs.core.events.BaseEvent;
import com.khodabandelu.cqrs.core.events.EventModel;
import com.khodabandelu.cqrs.core.exceptions.AggregateNotFoundException;
import com.khodabandelu.cqrs.core.exceptions.ConcurrencyException;
import com.khodabandelu.cqrs.core.infrastructure.EventStore;
import com.khodabandelu.cqrs.core.producers.EventProducer;
import com.khodabandelu.starbux.product.cmd.dao.EventStoreRepository;
import com.khodabandelu.starbux.product.cmd.domain.ProductAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductEventStore implements EventStore {
    private final EventProducer eventProducer;
    private final EventStoreRepository repository;
    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = repository.findByAggregateIdentifier(aggregateId);
        if (expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
            throw new ConcurrencyException();
        }
        var version = expectedVersion;
        for (var event : events) {
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .timeStamp(new Date())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(ProductAggregate.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();
            var persistedEvent = repository.save(eventModel);
            if (!persistedEvent.getId().isEmpty()) {
                this.eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        var eventStream = repository.findByAggregateIdentifier(aggregateId);
        if (eventStream == null || eventStream.isEmpty()) {
            throw new AggregateNotFoundException("Incorrect product ID provided!");
        }
        return eventStream.stream().map(EventModel::getEventData).collect(Collectors.toList());
    }

}