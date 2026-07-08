package com.subscribe.demo.subscribe.dto;

import java.util.UUID;
import software.amazon.awssdk.annotations.NotNull;

public record SubscriptionRequest(@NotNull UUID userId) {}
