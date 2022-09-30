package com.khodabandelu.starbux.cart.api.commands;

import com.khodabandelu.starbux.cart.domain.CartAggregate;
import com.khodabandelu.starbux.cart.dto.Product;
import com.khodabandelu.starbux.cart.services.CartService;
import com.khodabandelu.starbux.cart.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartCommandHandler implements CommandHandler {

    private final ProductService productService;

    private final CartService cartService;

    @Override
    public void handle(CreateCartCommand command) {
        if (!StringUtils.hasText(command.getProductId())) {
            throw new IllegalStateException("Product must be defined!");
        }
        var product = productService.findById(command.getProductId());
        List<Product> toppings = new ArrayList<>();
        command.getToppingIds().forEach(toppingId -> toppings.add(productService.findById(toppingId)));
        var aggregate = new CartAggregate(command, product, toppings);
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
        aggregate.removeItem(product, true);
        cartService.save(aggregate);
    }

    @Override
    public void handle(ConfirmCartCommand command) {
        var aggregate = cartService.getById(command.getId());
        aggregate.confirmCart();
        cartService.save(aggregate);
    }
}
