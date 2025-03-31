package com.example.routinegrowth.service;

import com.example.routinegrowth.DTO.UserRequest;
import com.example.routinegrowth.DTO.UserResponse;
import com.example.routinegrowth.entity.User;
import com.example.routinegrowth.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final Validator validator;

  public UserResponse createUser(UserRequest userRequest) {

    Set<ConstraintViolation<UserRequest>> violations =
        validator.validate(userRequest, UserRequest.Create.class);

    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }

    // userRequest to User entity
    User userToSave = User.builder().email(userRequest.getEmail()).build();

    // save user
    User userSaved = userRepository.save(userToSave);

    // User entity to UserResponse
    return UserResponse.builder().id(userSaved.getId()).email(userSaved.getEmail()).build();
  }
}
