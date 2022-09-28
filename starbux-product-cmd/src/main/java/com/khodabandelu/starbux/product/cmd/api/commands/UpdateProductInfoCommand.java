package com.khodabandelu.starbux.product.cmd.api.commands;

import com.khodabandelu.cqrs.core.commands.BaseCommand;
import com.khodabandelu.starbux.product.cmd.entities.CategoryType;
import lombok.Data;

@Data
public class UpdateProductInfoCommand extends BaseCommand {
    private String name;
    private CategoryType categoryType;
    private double price;
}
