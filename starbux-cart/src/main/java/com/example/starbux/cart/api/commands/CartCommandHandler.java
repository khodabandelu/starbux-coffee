package com.example.starbux.cart.api.commands;

import com.example.starbux.cart.domain.CartAggregate;
import com.example.starbux.cart.services.CartService;
import com.example.starbux.cart.services.ProductService;
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
        if (StringUtils.hasText(command.getProductId())) {
            throw new IllegalStateException("Product must be defined!");
        }
        var product = productService.findById(command.getProductId());
        var aggregate = new CartAggregate(command, product);
        cartService.save(aggregate);
    }

    @Override
    public void handle(AddItemCommand command) {
        var aggregate = cartService.getById(command.getId());
        var product = productService.findById(command.getProductId());
        var topping = productService.findById(command.getToppingId());
        aggregate.addItem(product, topping);
        cartService.save(aggregate);
    }

    @Override
    public void handle(RemoveItemCommand command) {
        var aggregate = cartService.getById(command.getId());
        var product = productService.findById(command.getProductId());
        aggregate.removeItem(product);
        cartService.save(aggregate);
    }

    @Override
    public void handle(ConfirmCartCommand command) {
        var aggregate = cartService.getById(command.getId());
        aggregate.confirmCart();
        cartService.save(aggregate);
    }
}
