package com.khodabandelu.cqrs.core.infrastructure;

import com.khodabandelu.cqrs.core.domain.BaseEntity;
import com.khodabandelu.cqrs.core.queries.BaseQuery;
import com.khodabandelu.cqrs.core.queries.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);

    <U extends BaseEntity> List<U> send(BaseQuery query);
}
