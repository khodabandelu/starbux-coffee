package com.khodabandelu.starbux.product.cmd.api.commands;

import com.khodabandelu.cqrs.core.commands.BaseCommand;
import com.khodabandelu.starbux.product.cmd.domain.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductCommand extends BaseCommand {
    private String name;
    private CategoryType categoryType;
    private double price;
}
