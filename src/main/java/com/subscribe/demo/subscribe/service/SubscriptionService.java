package com.subscribe.demo.subscribe.service;

import com.subscribe.demo.subscribe.entity.Course;
import com.subscribe.demo.subscribe.entity.Subscription;
import com.subscribe.demo.subscribe.entity.User;
import com.subscribe.demo.subscribe.repository.CourseRepository;
import com.subscribe.demo.subscribe.repository.SubscriptionRepository;
import com.subscribe.demo.subscribe.repository.UserRepository;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class SubscriptionService {

  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final SubscriptionRepository subscriptionRepository;

  @Transactional
  public Subscription subscribe(UUID userId, UUID courseId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

    Course course =
        courseRepository
            .findById(courseId)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

    if (subscriptionRepository.existsByUserIdAndCourseId(userId, courseId)) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "User Already Subscribed");
    }

    Subscription subscription =
        Subscription.builder().user(user).course(course).subscribedAt(Instant.now()).build();

    return subscriptionRepository.save(subscription);
  }
}
