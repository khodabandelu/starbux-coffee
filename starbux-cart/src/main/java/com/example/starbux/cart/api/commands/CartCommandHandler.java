package com.example.starbux.cart.api.commands;

import com.example.starbux.cart.services.CartService;
import com.example.starbux.cart.services.ProductService;
import com.example.starbux.cart.domain.CartAggregate;
import com.macan.cqrs.core.handlers.EventSourcingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class CartCommandHandler implements CommandHandler {

    private final ProductService productService;

    private final CartService cartService;

    @Override
    public void handle(OpenCartCommand command) {
        if (StringUtils.hasText(command.getDrinkProductId())) {
            throw new IllegalStateException("Product must be defined!");
        }
        var product = productService.findById(command.getDrinkProductId());
        var aggregate = new CartAggregate(command,product);
        cartService.save(aggregate);
    }

    @Override
    public void handle(AddItemCommand command) {
        var aggregate = cartService.getById(command.getId());
        var product = productService.findById(command.getDrinkProductId());
        aggregate.depositFunds(command.getAmount());
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(WithdrawFundsCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        if (command.getAmount() > aggregate.getBalance()) {
            throw new IllegalStateException("Withdrawal declined, insufficient funds!");
        }
        aggregate.withdrawFunds(command.getAmount());
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(CloseAccountCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.closeAccount();
        eventSourcingHandler.save(aggregate);
    }
}
