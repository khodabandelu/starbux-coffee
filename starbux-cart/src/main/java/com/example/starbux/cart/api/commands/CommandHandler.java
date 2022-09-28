package com.example.starbux.cart.api.commands;

public interface CommandHandler {

    void handle(OpenCartCommand command);

    void handle(AddItemCommand command);

    void handle(RemoveItemCommand command);

    void handle(ConfirmCartCommand command);

}
