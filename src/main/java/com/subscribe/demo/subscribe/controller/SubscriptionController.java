package com.subscribe.demo.subscribe.controller;

import com.subscribe.demo.endpoint.event.EventProducer;
import com.subscribe.demo.endpoint.event.model.CourseSubscribed;
import com.subscribe.demo.subscribe.dto.SubscriptionRequest;
import com.subscribe.demo.subscribe.dto.SubscriptionResponse;
import com.subscribe.demo.subscribe.entity.Subscription;
import com.subscribe.demo.subscribe.service.SubscriptionService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
@AllArgsConstructor
public class SubscriptionController {

  private final SubscriptionService subscriptionService;
  private final EventProducer<CourseSubscribed> eventProducer;

  @PostMapping("/{courseId}/subscriptions")
  @SneakyThrows
  public ResponseEntity<SubscriptionResponse> subscribe(
      @PathVariable UUID courseId, @RequestBody SubscriptionRequest request) {

    Subscription subscription = subscriptionService.subscribe(request.userId(), courseId);

    var event = CourseSubscribed.builder().subscriptionId(subscription.getId()).build();
    eventProducer.accept(List.of(event));

    return ResponseEntity.status(HttpStatus.CREATED).body(SubscriptionResponse.from(subscription));
  }
}
