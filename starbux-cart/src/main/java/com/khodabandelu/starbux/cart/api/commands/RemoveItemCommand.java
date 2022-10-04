package com.khodabandelu.starbux.cart.api.commands;

import com.khodabandelu.cqrs.core.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RemoveItemCommand extends BaseCommand {
    private String productId;
}
