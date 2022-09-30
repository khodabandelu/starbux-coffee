package com.khodabandelu.starbux.cart.api.commands;

import com.khodabandelu.cqrs.core.commands.BaseCommand;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreateCartCommand extends BaseCommand {
    @NotNull
    private String customer;
    @NotNull
    private String productId;

    private List<String> toppingIds;

    public List<String> getToppingIds() {
        if (this.toppingIds == null)
            return new ArrayList<>();
        return toppingIds;
    }
}
