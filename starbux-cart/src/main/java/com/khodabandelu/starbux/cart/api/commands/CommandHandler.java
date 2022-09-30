package com.khodabandelu.starbux.cart.api.commands;

public interface CommandHandler {

    void handle(CreateCartCommand command);

    void handle(AddItemCommand command);

    void handle(RemoveItemCommand command);

    void handle(ConfirmCartCommand command);

}
