package com.example.routinegrowth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.routinegrowth.DTO.UserRequest;
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
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@DisplayName("User Controller Test")
@TestMethodOrder(MethodOrderer.Random.class)
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper = new ObjectMapper();

  @Test
  @DisplayName("User Registration Test : Success")
  public void registerUserTest_success() throws Exception {
    // make user request object
    UserRequest userRequest =
        UserRequest.builder().email("test@gmail.com").password("testtest").build();

    // register user
    mockMvc
        .perform(
            post("/api/user/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userRequest)))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("User Registration Test : Fail")
  public void registerUserTest_fail() throws Exception {
    // make invalid user request object
    UserRequest userRequest = new UserRequest();

    mockMvc
        .perform(
            post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.email").value("이메일은 비어있을 수 없습니다."))
        .andExpect(jsonPath("$.password").value("비밀번호는 비어있을 수 없습니다."));
  }
}
