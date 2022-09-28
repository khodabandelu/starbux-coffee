package com.khodabandelu.starbux.product.cmd.api.commands;

public interface CommandHandler {

    void handle(CreateProductCommand command);

    void handle(DeactivateProductCommand command);

    void handle(DeleteProductCommand command);

    void handle(UpdateProductInfoCommand command);

}
