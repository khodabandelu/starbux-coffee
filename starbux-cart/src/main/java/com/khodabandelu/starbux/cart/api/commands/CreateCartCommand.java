package com.khodabandelu.starbux.cart.api.commands;

import com.khodabandelu.cqrs.core.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
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
