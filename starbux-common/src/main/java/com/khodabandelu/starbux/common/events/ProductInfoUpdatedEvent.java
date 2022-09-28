package com.khodabandelu.starbux.common.events;

import com.khodabandelu.cqrs.core.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProductInfoUpdatedEvent extends BaseEvent {
    private String name;
    private String categoryType;
    private double price;
}