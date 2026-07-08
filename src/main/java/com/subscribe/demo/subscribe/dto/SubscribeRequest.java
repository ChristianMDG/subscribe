package com.subscribe.demo.subscribe.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscribeRequest {

  @NotNull(message = "userId est obligatoire")
  private UUID userId;
}
