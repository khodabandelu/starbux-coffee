package com.khodabandelu.starbux.product.cmd.infrastructure;

import com.khodabandelu.cqrs.core.domain.AggregateRoot;
import com.khodabandelu.cqrs.core.events.BaseEvent;
import com.khodabandelu.cqrs.core.handlers.EventSourcingHandler;
import com.khodabandelu.cqrs.core.infrastructure.EventStore;
import com.khodabandelu.starbux.product.cmd.domain.ProductAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class ProductEventSourcingHandler implements EventSourcingHandler<ProductAggregate> {
    private final EventStore eventStore;

    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
        aggregate.markChangesAsCommitted();
    }

    @Override
    public ProductAggregate getById(String id) {
        var aggregate = new ProductAggregate();
        var events = eventStore.getEvents(id);
        if (events != null && !events.isEmpty()) {
            aggregate.replayEvents(events);
            var latestVersion = events.stream().map(BaseEvent::getVersion).max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }
        return aggregate;
    }

}