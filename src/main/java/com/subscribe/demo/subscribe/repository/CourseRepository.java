package com.subscribe.demo.subscribe.repository;

import com.subscribe.demo.subscribe.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<
        Course, UUID> {
}