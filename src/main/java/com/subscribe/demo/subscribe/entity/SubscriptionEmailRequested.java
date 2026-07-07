package com.subscribe.demo.subscribe.entity;

import com.course.demo.endpoint.event.model.PojaEvent;
import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionEmailRequested extends PojaEvent {
  private String to;
  private String userFirstName;
  private String courseTitle;

  @Override
  public Duration maxConsumerDuration() {
    return Duration.ofSeconds(30);
  }

  @Override
  public Duration maxConsumerBackoffBetweenRetries() {
    return Duration.ofSeconds(5);
  }
}
