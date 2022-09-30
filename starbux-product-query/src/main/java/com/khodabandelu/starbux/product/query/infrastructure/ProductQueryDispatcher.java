package com.khodabandelu.starbux.product.query.infrastructure;

import com.khodabandelu.cqrs.core.domain.BaseEntity;
import com.khodabandelu.cqrs.core.infrastructure.QueryDispatcher;
import com.khodabandelu.cqrs.core.queries.BaseQuery;
import com.khodabandelu.cqrs.core.queries.AggregateQuery;
import com.khodabandelu.cqrs.core.queries.AggregateQueryHandlerMethod;
import com.khodabandelu.cqrs.core.queries.QueryHandlerMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class ProductQueryDispatcher implements QueryDispatcher {

    private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();
    private final Map<Class<? extends AggregateQuery>, List<AggregateQueryHandlerMethod>> aggregateRoutes = new HashMap<>();

    @Override
    public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public <T extends AggregateQuery> void registerHandler(Class<T> type, AggregateQueryHandlerMethod<T> handler) {
        var handlers = aggregateRoutes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public <U extends BaseEntity> List<U> send(BaseQuery query) {
        var handlers = routes.get(query.getClass());
        if (handlers == null || handlers.size() <= 0) {
            throw new RuntimeException("No query handler was registered");
        }
        if (handlers.size() > 1) {
            throw new RuntimeException("Cannot send query to more than one handler");
        }
        return handlers.get(0).handle(query);
    }

    @Override
    public <U extends Number> Number send(AggregateQuery query) {
        var handlers = aggregateRoutes.get(query.getClass());
        if (handlers == null || handlers.size() <= 0) {
            throw new RuntimeException("No query handler was registered");
        }
        if (handlers.size() > 1) {
            throw new RuntimeException("Cannot send query to more than one handler");
        }
        return handlers.get(0).handle(query);
    }
}
