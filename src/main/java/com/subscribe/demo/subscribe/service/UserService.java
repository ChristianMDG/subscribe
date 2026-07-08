package com.subscribe.demo.subscribe.service;

import com.subscribe.demo.subscribe.dto.UserRequest;
import com.subscribe.demo.subscribe.entity.User;
import com.subscribe.demo.subscribe.exception.UserNotFoundException;
import com.subscribe.demo.subscribe.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  @Transactional
  public User create(UserRequest request) {
    User user = new User();
    applyRequest(user, request);
    return userRepository.save(user);
  }

  public List<User> getAll() {
    return userRepository.findAll();
  }

  public User getById(UUID id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé avec l'ID: " + id));
  }

  @Transactional
  public User update(UUID id, UserRequest request) {
    User user = getById(id);
    applyRequest(user, request);
    return userRepository.save(user);
  }

  @Transactional
  public void delete(UUID id) {
    if (!userRepository.existsById(id)) {
      throw new UserNotFoundException("Utilisateur non trouvé avec l'ID: " + id);
    }
    userRepository.deleteById(id);
  }

  private void applyRequest(User user, UserRequest request) {
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setUserName(request.getUserName());
    user.setEmail(request.getEmail());
  }
}
