package com.khodabandelu.cqrs.core.queries;

@FunctionalInterface
public interface AggregateQueryHandlerMethod<T extends AggregateQuery> {
     Number handle(T query);
}
