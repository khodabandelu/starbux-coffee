package com.khodabandelu.starbux.cart.api.commands;

import com.khodabandelu.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class ConfirmCartCommand extends BaseCommand {
    public ConfirmCartCommand(String id) {
        super(id);
    }
}
