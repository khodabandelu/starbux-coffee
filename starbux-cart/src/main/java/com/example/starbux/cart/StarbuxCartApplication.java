package com.example.starbux.cart;

import com.example.starbux.cart.api.commands.*;
import com.khodabandelu.cqrs.core.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
public class StarbuxCartApplication {

    private final CommandDispatcher commandDispatcher;
    private final CommandHandler commandHandler;

    public static void main(String[] args) {
        SpringApplication.run(StarbuxCartApplication.class, args);
    }

    @PostConstruct
    public void registerHandler() {
        commandDispatcher.registerHandler(OpenCartCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(AddItemCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(RemoveItemCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(ConfirmCartCommand.class, commandHandler::handle);
    }
}
