package com.example.routinegrowth;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.routinegrowth.DTO.UserRequest;
import com.example.routinegrowth.DTO.UserResponse;
import com.example.routinegrowth.service.UserService;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@DisplayName("UserService Test")
@TestMethodOrder(MethodOrderer.Random.class)
@Transactional
public class UserServiceTest {

  @Autowired private UserService userService;

  /**
   * Test for register user
   *
   * @see UserService#createUser(UserRequest)
   */
  @Test
  @DisplayName("Register User Test : Success")
  public void registerUserTest_success() {
    // make user request object
    UserRequest userRequest = UserRequest.builder().email("register@test").password("test").build();

    // register user
    UserResponse user_created = userService.createUser(userRequest);

    // expectation
    assertThat(user_created).isNotNull();
    assertThat(user_created.getEmail()).isEqualTo(userRequest.getEmail());
  }

  /**
   * Test for register user
   *
   * @see UserService#createUser(UserRequest)
   */
  @Test
  @DisplayName("Register User Test : Fail")
  public void registerUserTest_fail() {
    // make empty user request object
    UserRequest userRequest = new UserRequest();

    assertThrows(
        ConstraintViolationException.class,
        () -> {
          // register user with empty request
          userService.createUser(userRequest);
        });
  }

  /**
   * Test for login user
   *
   * @see UserService#login(UserRequest)
   */
  @Test
  @DisplayName("User Login Test : Success")
  public void loginTest_success() throws Exception {
    // make user request object
    UserRequest userRequest = UserRequest.builder().email("register@test").password("test").build();

    // register user
    userService.createUser(userRequest);

    // login user
    UserResponse user_loggedIn = userService.login(userRequest);

    // expectation
    assertThat(user_loggedIn).isNotNull();
    assertThat(user_loggedIn.getEmail()).isEqualTo(userRequest.getEmail());
  }

  /**
   * Test for login user
   *
   * @see UserService#login(UserRequest)
   */
  @Test
  @DisplayName("User Login Test : Fail")
  public void loginTest_fail() {
    // make user request object
    UserRequest userRequest = UserRequest.builder().email("register@test").build();

    // try to logged-in without registering
    Exception exception = assertThrows(Exception.class, () -> userService.login(userRequest));

    // expectation
    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("User not found");
  }
}
