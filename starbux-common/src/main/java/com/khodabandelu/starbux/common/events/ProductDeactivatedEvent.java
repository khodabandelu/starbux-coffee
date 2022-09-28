package com.khodabandelu.starbux.common.events;

import com.khodabandelu.cqrs.core.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
public class ProductDeactivatedEvent extends BaseEvent {
}