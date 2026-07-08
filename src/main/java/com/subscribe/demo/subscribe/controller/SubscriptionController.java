package com.subscribe.demo.subscribe.controller;

import com.subscribe.demo.endpoint.event.EventProducer;
import com.subscribe.demo.endpoint.event.model.SubscriptionCreatedEvent;

import com.subscribe.demo.service.event.SubscriptionCreationResult;
import com.subscribe.demo.subscribe.dto.SubscribeRequest;
import com.subscribe.demo.subscribe.dto.SubscriptionResponse;
import com.subscribe.demo.subscribe.entity.Subscription;
import com.subscribe.demo.subscribe.service.SubscriptionService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/course")
@AllArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final EventProducer<SubscriptionCreatedEvent> eventProducer;

    @PostMapping("/{courseId}/subscribe")
    @SneakyThrows
    public ResponseEntity<SubscriptionResponse> subscribe(
            @PathVariable UUID courseId,
            @Valid @RequestBody SubscribeRequest request) {

        SubscriptionCreationResult result = subscriptionService.subscribe(courseId, request.getUserId());
        Subscription subscription = result.subscription();

        // Déclenche le traitement asynchrone : PDF + S3 + email
        eventProducer.accept(List.of(result.event()));

        SubscriptionResponse response = SubscriptionResponse.builder()
                .subscriptionId(subscription.getId())
                .userId(subscription.getUser().getId())
                .courseId(subscription.getCourse().getId())
                .status(subscription.getStatus())
                .subscriptionDate(subscription.getSubscriptionDate())
                .message("Inscription enregistrée. Email avec ticket en cours d'envoi.")
                .build();

        return ResponseEntity.ok(response);
    }
}