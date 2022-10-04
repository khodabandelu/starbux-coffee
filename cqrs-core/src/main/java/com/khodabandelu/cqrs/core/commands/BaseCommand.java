package com.khodabandelu.cqrs.core.commands;

import com.khodabandelu.cqrs.core.messages.Message;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public abstract class BaseCommand extends Message {
    public BaseCommand(String id) {
        super(id);
    }
}
