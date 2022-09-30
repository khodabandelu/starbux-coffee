package com.khodabandelu.starbux.product.cmd.api.commands;

import com.khodabandelu.cqrs.core.handlers.EventSourcingHandler;
import com.khodabandelu.starbux.product.cmd.domain.ProductAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCommandHandler implements CommandHandler {
    private final EventSourcingHandler<ProductAggregate> eventSourcingHandler;

    @Override
    public void handle(CreateProductCommand command) {
        var aggregate = new ProductAggregate(command);
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(UpdateProductInfoCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.updateProductInfo(command);
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(DeleteProductCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        //todo if product has not any used event in other systems you can delete all events based  on this product id
        aggregate.deactivateProduct();
        eventSourcingHandler.save(aggregate);
    }

}
