package com.khodabandelu.starbux.product.cmd.api.commands;

import com.khodabandelu.cqrs.core.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
public class DeleteProductCommand extends BaseCommand {
    public DeleteProductCommand(String id) {
        super(id);
    }
}
