package com.example.starbux.cart.api.commands;

import com.khodabandelu.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class OpenCartCommand extends BaseCommand {
    private String customer;
    private String drinkProductId;
//    private String toppingProductId;
}
