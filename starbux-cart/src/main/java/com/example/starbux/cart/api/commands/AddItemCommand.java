package com.example.starbux.cart.api.commands;

import com.khodabandelu.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class AddItemCommand extends BaseCommand {
    private String productId;
    private String toppingId;
}
