package com.khodabandelu.starbux.product.query;

import com.khodabandelu.starbux.product.query.api.queries.*;
import com.khodabandelu.cqrs.core.infrastructure.QueryDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class StarbuxProductQueryApplication {

    @Autowired
    QueryDispatcher queryDispatcher;

    @Autowired
    QueryHandler queryHandler;

    public static void main(String[] args) {
        SpringApplication.run(StarbuxProductQueryApplication.class, args);
    }

    @PostConstruct
    public void registerHandlers() {
        queryDispatcher.registerHandler(FindAllProductsQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(FindProductByIdQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(FindProductByCategoryQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(FindProductByPriceQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(FindProductByNameQuery.class, queryHandler::handle);
    }

}
