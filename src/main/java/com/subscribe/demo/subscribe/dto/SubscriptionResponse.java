package com.subscribe.demo.subscribe.dto;

import com.subscribe.demo.subscribe.entity.Subscription;
import java.time.Instant;
import java.util.UUID;

public record SubscriptionResponse(UUID id, UUID userId, UUID courseId, Instant subscribedAt) {

  public static SubscriptionResponse from(Subscription subscription) {
    return new SubscriptionResponse(
        subscription.getId(),
        subscription.getUser().getId(),
        subscription.getCourse().getId(),
        subscription.getSubscribedAt());
  }
}
