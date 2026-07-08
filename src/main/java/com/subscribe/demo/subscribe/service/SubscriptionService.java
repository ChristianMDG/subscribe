package com.subscribe.demo.subscribe.service;


import com.subscribe.demo.endpoint.event.model.SubscriptionCreatedEvent;

import com.subscribe.demo.service.event.SubscriptionCreationResult;
import com.subscribe.demo.subscribe.entity.Course;
import com.subscribe.demo.subscribe.entity.Subscription;
import com.subscribe.demo.subscribe.entity.SubscriptionStatus;
import com.subscribe.demo.subscribe.entity.User;
import com.subscribe.demo.subscribe.exception.AlreadySubscribedException;
import com.subscribe.demo.subscribe.exception.CourseNotFoundException;
import com.subscribe.demo.subscribe.exception.UserNotFoundException;
import com.subscribe.demo.subscribe.repository.CourseRepository;
import com.subscribe.demo.subscribe.repository.SubscriptionRepository;
import com.subscribe.demo.subscribe.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SubscriptionService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    public SubscriptionCreationResult subscribe(UUID courseId, UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        "Utilisateur non trouvé avec l'ID: " + userId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(
                        "Cours non trouvé avec l'ID: " + courseId));

        if (subscriptionRepository.existsByUserIdAndCourseId(userId, courseId)) {
            throw new AlreadySubscribedException("Déjà inscrit à ce cours");
        }

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setCourse(course);
        subscription.setSubscriptionDate(Instant.now());
        subscription.setStatus(SubscriptionStatus.PENDING);
        subscription = subscriptionRepository.save(subscription);

        SubscriptionCreatedEvent event = SubscriptionCreatedEvent.builder()
                .subscriptionId(subscription.getId())
                .userId(user.getId())
                .userFirstName(user.getFirstName())
                .userLastName(user.getLastName())
                .userEmail(user.getEmail())
                .courseId(course.getId())
                .courseTitle(course.getTitle())
                .courseStartDate(course.getStartDate())
                .courseEndDate(course.getEndDate())
                .build();

        return new SubscriptionCreationResult(subscription, event);
    }
}