package com.subscribe.demo.subscribe.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class SubscribeRequest {

    @NotNull(message = "userId est obligatoire")
    private UUID userId;
}