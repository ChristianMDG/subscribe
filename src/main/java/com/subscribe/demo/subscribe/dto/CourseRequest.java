package com.subscribe.demo.subscribe.dto;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseRequest {
  private String title;
  private Instant startDate;
  private Instant endDate;
}
