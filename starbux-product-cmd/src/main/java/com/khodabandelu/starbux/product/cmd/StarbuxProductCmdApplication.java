package com.khodabandelu.starbux.product.cmd;

import com.khodabandelu.cqrs.core.infrastructure.CommandDispatcher;
import com.khodabandelu.starbux.product.cmd.api.commands.CommandHandler;
import com.khodabandelu.starbux.product.cmd.api.commands.CreateProductCommand;
import com.khodabandelu.starbux.product.cmd.api.commands.DeactivateProductCommand;
import com.khodabandelu.starbux.product.cmd.api.commands.UpdateProductInfoCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
public class StarbuxProductCmdApplication {
    private final CommandDispatcher commandDispatcher;
    private final CommandHandler commandHandler;

    public static void main(String[] args) {
        SpringApplication.run(StarbuxProductCmdApplication.class, args);
    }

    @PostConstruct
    public void registerHandler() {
        commandDispatcher.registerHandler(CreateProductCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(UpdateProductInfoCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(DeactivateProductCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(DeactivateProductCommand.class, commandHandler::handle);
    }

}
