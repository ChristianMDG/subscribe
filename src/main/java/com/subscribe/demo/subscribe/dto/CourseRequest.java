package com.subscribe.demo.subscribe.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseRequest {
  @NotBlank private String title;
  @NotNull @Future private Instant startDate;
  @NotNull private Instant endDate;
}
