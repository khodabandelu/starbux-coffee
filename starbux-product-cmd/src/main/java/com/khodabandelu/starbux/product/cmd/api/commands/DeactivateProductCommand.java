package com.khodabandelu.starbux.product.cmd.api.commands;

import com.khodabandelu.cqrs.core.commands.BaseCommand;

public class DeactivateProductCommand extends BaseCommand {

    public DeactivateProductCommand(String id){
        super(id);
    }
}