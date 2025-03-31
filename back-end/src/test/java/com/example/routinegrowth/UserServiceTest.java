package com.example.routinegrowth;

import com.example.routinegrowth.DTO.UserRequest;
import com.example.routinegrowth.DTO.UserResponse;
import com.example.routinegrowth.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@DisplayName("UserService Test")
@TestMethodOrder(MethodOrderer.Random.class)
@Transactional
public class UserServiceTest {

  @Autowired private UserService userService;

  @Test
  @DisplayName("Register User Test : Success")
  public void registerUserTest() {
    // make user request object
    UserRequest userRequest = UserRequest.builder().email("register@test").build();

    // register user
    UserResponse user_created = userService.createUser(userRequest);

    // expectation
    assertThat(user_created).isNotNull();
    assertThat(user_created.getEmail()).isEqualTo(userRequest.getEmail());
  }



}
