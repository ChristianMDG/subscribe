package com.subscribe.demo.subscribe.repository;

import com.subscribe.demo.subscribe.entity.Subscription;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
  boolean existsByUserIdAndCourseId(UUID userId, UUID courseId);
}
