package com.khodabandelu.starbux.cart.api.commands;

import com.khodabandelu.cqrs.core.commands.BaseCommand;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ConfirmCartCommand extends BaseCommand {
    public ConfirmCartCommand(String id) {
        super(id);
    }
}
