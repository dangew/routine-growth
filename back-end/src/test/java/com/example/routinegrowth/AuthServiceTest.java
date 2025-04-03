package com.example.routinegrowth;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.routinegrowth.DTO.UserRequest;
import com.example.routinegrowth.auth.dto.AuthRequest;
import com.example.routinegrowth.auth.jwt.JwtUtil;
import com.example.routinegrowth.auth.service.AuthService;
import com.example.routinegrowth.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@DisplayName("AuthService Test (JWT)")
@TestMethodOrder(MethodOrderer.Random.class)
@AutoConfigureMockMvc
public class AuthServiceTest {

  @Autowired private AuthService authService;
  @Autowired private UserService userService;
  @Autowired private JwtUtil jwtUtil;
  @Autowired private MockMvc mockMvc;

  @Test
  @DisplayName("Login Test")
  public void loginTest_success() throws Exception {
    // make user request object
    UserRequest userRequest = UserRequest.builder().email("login@test").password("test").build();

    // register user
    userService.createUser(userRequest);

    // login user
    String login_token = authService.login(userRequest.getEmail(), userRequest.getPassword());

    // expectation
    assertThat(jwtUtil.validate(login_token)).isTrue();
    assertThat(jwtUtil.getUserEmail(login_token)).isEqualTo(userRequest.getEmail());
  }

  @Test
  @DisplayName("Login Test : Success")
  public void loginTest_success_controller() throws Exception {

    // make user request object
    UserRequest userRequest = UserRequest.builder().email("login_controller@test").password("test").build();
    // register user
    userService.createUser(userRequest);

    // make auth request object
    AuthRequest authRequest =
      AuthRequest.builder().email("login_controller@test").password("test").build();

    // make object mapper
    String content = new ObjectMapper().writeValueAsString(authRequest);

    // login user
    mockMvc.perform(
      post("/api/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(content)
    )
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.token").exists())
      .andDo(print())
      .andReturn();
  }
}
