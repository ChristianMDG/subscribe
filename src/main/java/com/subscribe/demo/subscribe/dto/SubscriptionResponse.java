package com.subscribe.demo.subscribe.dto;

import com.subscribe.demo.subscribe.entity.SubscriptionStatus;
import lombok.Builder;
import lombok.Getter;
import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class SubscriptionResponse {
    private UUID subscriptionId;
    private UUID userId;
    private UUID courseId;
    private SubscriptionStatus status;
    private Instant subscriptionDate;
    private String message;
}