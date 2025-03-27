package com.example.routinegrowth.service;

import com.example.routinegrowth.DTO.UserRequest;
import com.example.routinegrowth.DTO.UserResponse;
import com.example.routinegrowth.entity.User;
import com.example.routinegrowth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public UserResponse createUser(UserRequest userRequest) {

    // userRequest to User entity
    User userToSave = User.builder().email(userRequest.getEmail()).build();

    // save user
    User userSaved = userRepository.save(userToSave);

    // User entity to UserResponse
    return UserResponse.builder().id(userSaved.getId()).email(userSaved.getEmail()).build();
  }
}
