package com.subscribe.demo.service.event;

import com.subscribe.demo.endpoint.event.model.SubscriptionCreatedEvent;
import com.subscribe.demo.subscribe.entity.Subscription;

public record SubscriptionCreationResult(
    Subscription subscription, SubscriptionCreatedEvent event) {}
