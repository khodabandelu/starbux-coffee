package com.example.starbux.cart.api.commands;

import com.khodabandelu.cqrs.core.commands.BaseCommand;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OpenCartCommand extends BaseCommand {
    @NotNull
    private String customer;
    @NotNull
    private String productId;
//    private String toppingProductId;
}
