package com.investment.events.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public abstract class DomainEvent {
    protected UUID id = UUID.randomUUID();
    protected Instant dateCreated = Instant.now();

    public abstract DomainEventType eventType();
}
