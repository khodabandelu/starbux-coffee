package com.khodabandelu.starbux.product.cmd.api.commands;

import com.khodabandelu.cqrs.core.commands.BaseCommand;

public class DeleteProductCommand extends BaseCommand {
    public DeleteProductCommand(String id) {
        super(id);
    }
}
