package com.khodabandelu.starbux.common.events;

import com.khodabandelu.cqrs.core.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class ProductDeletedEvent extends BaseEvent {
}