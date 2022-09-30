package com.khodabandelu.cqrs.core.infrastructure;

import com.khodabandelu.cqrs.core.domain.BaseEntity;
import com.khodabandelu.cqrs.core.queries.BaseQuery;
import com.khodabandelu.cqrs.core.queries.AggregateQuery;
import com.khodabandelu.cqrs.core.queries.AggregateQueryHandlerMethod;
import com.khodabandelu.cqrs.core.queries.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);
    <T extends AggregateQuery> void registerHandler(Class<T> type, AggregateQueryHandlerMethod<T> handler);

    <U extends BaseEntity> List<U> send(BaseQuery query);

    <U extends Number> Number send(AggregateQuery query);
}
