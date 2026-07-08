package com.subscribe.demo.subscribe.repository;

import com.subscribe.demo.subscribe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}