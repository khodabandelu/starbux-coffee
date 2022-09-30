package com.khodabandelu.starbux.product.cmd.api.commands;

import com.khodabandelu.cqrs.core.commands.BaseCommand;
import com.khodabandelu.starbux.product.cmd.domain.CategoryType;
import lombok.Data;

@Data
public class CreateProductCommand extends BaseCommand {
    private String name;
    private CategoryType categoryType;
    private double price;
}
