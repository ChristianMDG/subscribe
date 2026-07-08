package com.subscribe.demo.subscribe.controller;

import com.subscribe.demo.subscribe.dto.UserRequest;
import com.subscribe.demo.subscribe.entity.User;
import com.subscribe.demo.subscribe.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<User> create(@Valid @RequestBody UserRequest request) {
    return ResponseEntity.ok(userService.create(request));
  }

  @GetMapping
  public List<User> getAll() {
    return userService.getAll();
  }

  @GetMapping("/{id}")
  public User getById(@PathVariable UUID id) {
    return userService.getById(id);
  }

  @PutMapping("/{id}")
  public User update(@PathVariable UUID id, @Valid @RequestBody UserRequest request) {
    return userService.update(id, request);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
