package com.khodabandelu.starbux.cart.api.commands;

import com.khodabandelu.cqrs.core.commands.BaseCommand;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddItemCommand extends BaseCommand {
    @NotNull
    private String productId;
    private String toppingId;
}
