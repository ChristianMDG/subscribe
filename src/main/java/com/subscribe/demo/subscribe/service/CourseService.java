package com.subscribe.demo.subscribe.service;

import com.subscribe.demo.subscribe.dto.CourseRequest;
import com.subscribe.demo.subscribe.entity.Course;
import com.subscribe.demo.subscribe.exception.CourseNotFoundException;
import com.subscribe.demo.subscribe.exception.InvalidCourseDatesException;
import com.subscribe.demo.subscribe.repository.CourseRepository;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CourseService {

  private final CourseRepository courseRepository;

  @Transactional
  public Course create(CourseRequest request) {
    validateDates(request.getStartDate(), request.getEndDate());
    Course course = new Course();
    applyRequest(course, request);
    return courseRepository.save(course);
  }

  public List<Course> getAll() {
    return courseRepository.findAll();
  }

  public Course getById(UUID id) {
    return courseRepository
        .findById(id)
        .orElseThrow(() -> new CourseNotFoundException("Cours non trouvé avec l'ID: " + id));
  }

  @Transactional
  public Course update(UUID id, CourseRequest request) {
    validateDates(request.getStartDate(), request.getEndDate());
    Course course = getById(id);
    applyRequest(course, request);
    return courseRepository.save(course);
  }

  @Transactional
  public void delete(UUID id) {
    if (!courseRepository.existsById(id)) {
      throw new CourseNotFoundException("Cours non trouvé avec l'ID: " + id);
    }
    courseRepository.deleteById(id);
  }

  private void applyRequest(Course course, CourseRequest request) {
    course.setTitle(request.getTitle());
    course.setStartDate(request.getStartDate());
    course.setEndDate(request.getEndDate());
  }

  private void validateDates(Instant start, Instant end) {
    if (!end.isAfter(start)) {
      throw new InvalidCourseDatesException("La date de fin doit être après la date de début");
    }
  }
}
