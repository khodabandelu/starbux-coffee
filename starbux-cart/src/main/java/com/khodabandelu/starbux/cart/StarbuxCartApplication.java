package com.khodabandelu.starbux.cart;

import com.khodabandelu.cqrs.core.infrastructure.QueryDispatcher;
import com.khodabandelu.starbux.cart.api.commands.*;
import com.khodabandelu.cqrs.core.infrastructure.CommandDispatcher;
import com.khodabandelu.starbux.cart.api.queries.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
public class StarbuxCartApplication {

    private final CommandDispatcher commandDispatcher;
    private final CommandHandler commandHandler;

    private final QueryDispatcher queryDispatcher;

    private final QueryHandler queryHandler;

    public static void main(String[] args) {
        SpringApplication.run(StarbuxCartApplication.class, args);
    }

    @PostConstruct
    public void registerHandler() {
        commandDispatcher.registerHandler(CreateCartCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(AddItemCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(RemoveItemCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(ConfirmCartCommand.class, commandHandler::handle);

        queryDispatcher.registerHandler(FindAllCartsQuery.class,queryHandler::handle);
        queryDispatcher.registerHandler(FindCartByIdQuery.class,queryHandler::handle);
        queryDispatcher.registerHandler(FindCartByCustomerQuery.class,queryHandler::handle);
        queryDispatcher.registerHandler(FindCurrentCartByCustomerQuery.class,queryHandler::handle);
        queryDispatcher.registerHandler(FindLastCartByCustomerQuery.class,queryHandler::handle);
        queryDispatcher.registerHandler(FindLastConfimredCartByCustomerQuery.class,queryHandler::handle);
        queryDispatcher.registerHandler(FindTotalPriceAllCartsByCustomerQuery.class,queryHandler::handle);
        queryDispatcher.registerHandler(FindMostUsedToppingQuery.class,queryHandler::handle);
    }
}
