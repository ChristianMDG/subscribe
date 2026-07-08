package com.subscribe.demo.subscribe.controller;

import com.subscribe.demo.subscribe.dto.CourseRequest;
import com.subscribe.demo.subscribe.entity.Course;
import com.subscribe.demo.subscribe.service.CourseService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseController {

  private final CourseService courseService;

  @PostMapping
  public ResponseEntity<Course> create(@Valid @RequestBody CourseRequest request) {
    return ResponseEntity.ok(courseService.create(request));
  }

  @GetMapping
  public List<Course> getAll() {
    return courseService.getAll();
  }

  @GetMapping("/{id}")
  public Course getById(@PathVariable UUID id) {
    return courseService.getById(id);
  }

  @PutMapping("/{id}")
  public Course update(@PathVariable UUID id, @Valid @RequestBody CourseRequest request) {
    return courseService.update(id, request);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    courseService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
