package com.subscribe.demo.subscribe.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
  @NotBlank private String firstName;

  @NotBlank private String lastName;

  @NotBlank private String userName;

  @NotBlank @Email private String email;
}
