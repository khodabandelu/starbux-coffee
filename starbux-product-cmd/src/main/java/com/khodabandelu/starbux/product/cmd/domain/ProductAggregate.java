package com.khodabandelu.starbux.product.cmd.domain;

import com.khodabandelu.cqrs.core.domain.AggregateRoot;
import com.khodabandelu.starbux.common.events.ProductCreatedEvent;
import com.khodabandelu.starbux.common.events.ProductDeactivatedEvent;
import com.khodabandelu.starbux.common.events.ProductInfoUpdatedEvent;
import com.khodabandelu.starbux.product.cmd.api.commands.CreateProductCommand;
import com.khodabandelu.starbux.product.cmd.api.commands.UpdateProductInfoCommand;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class ProductAggregate extends AggregateRoot {
    private Boolean active;
    public ProductAggregate(CreateProductCommand command) {
        raiseEvent(ProductCreatedEvent.builder()
                .id(command.getId())
                .name(command.getName())
                .createdDate(new Date())
                .categoryType(command.getCategoryType().name())
                .price(command.getPrice())
                .build());
    }

    public void apply(ProductCreatedEvent event) {
        this.id = event.getId();
        this.active = true;
    }

    public void updateProductInfo(UpdateProductInfoCommand command) {
        if (!this.active) {
            throw new IllegalStateException("Update product data can not be done into a deactivated product!");
        }
        raiseEvent(ProductInfoUpdatedEvent.builder()
                .id(this.id)
                .name(command.getName())
                .categoryType(command.getCategoryType().name())
                .build());
    }

    public void apply(UpdateProductInfoCommand event) {
        this.id = event.getId();
    }

    public void deactivateProduct() {
        if (!this.active) {
            throw new IllegalStateException("The product has already been deactivated!");
        }
        raiseEvent(ProductDeactivatedEvent.builder()
                .id(this.id)
                .build());
    }

    public void apply(ProductDeactivatedEvent event) {
        this.id = event.getId();
        this.active = false;
    }
}
