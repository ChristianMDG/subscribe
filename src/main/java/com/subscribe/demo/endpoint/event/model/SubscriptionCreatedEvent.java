package com.subscribe.demo.endpoint.event.model;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class SubscriptionCreatedEvent extends PojaEvent {

  private UUID subscriptionId;
  private UUID userId;
  private String userFirstName;
  private String userLastName;
  private String userEmail;
  private UUID courseId;
  private String courseTitle;
  private Instant courseStartDate;
  private Instant courseEndDate;

  @Override
  public Duration maxConsumerDuration() {
    // génération PDF + upload S3 + envoi email : on laisse une marge confortable
    return Duration.ofSeconds(60);
  }

  @Override
  public Duration maxConsumerBackoffBetweenRetries() {
    return Duration.ofSeconds(30);
  }
}
