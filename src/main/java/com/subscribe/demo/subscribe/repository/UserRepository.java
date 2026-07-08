package com.subscribe.demo.subscribe.repository;

import com.subscribe.demo.subscribe.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findById(UUID userId);
}
