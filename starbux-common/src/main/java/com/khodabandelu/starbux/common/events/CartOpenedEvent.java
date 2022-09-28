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
public class CartOpenedEvent extends BaseEvent {
    private String customer;
    private Date createdDate;
    private String drinkProductId;
    private String toppingProductId;
}