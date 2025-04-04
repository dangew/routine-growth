package com.example.routinegrowth;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.routinegrowth.DTO.RoutineRequest;
import com.example.routinegrowth.auth.jwt.JwtUtil;
import com.example.routinegrowth.common.BaseServiceTest;
import com.example.routinegrowth.service.RoutineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@DisplayName("Routine Controller Test")
@TestMethodOrder(MethodOrderer.Random.class)
@AutoConfigureMockMvc
@Transactional
public class RoutineControllerTest extends BaseServiceTest {
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private JwtUtil jwtUtil;
  @Autowired private RoutineService routineService;

  /**
   * Routine Creation Test
   *
   * @throws Exception api call exception
   */
  @Test
  @DisplayName("Routine Creation Test")
  public void routineCreation_success() throws Exception {
    // make routine request object
    RoutineRequest routineRequest =
        RoutineRequest.builder()
            .categoryId(routineCategoryResponseStudy.getId())
            .content("Study Routine")
            .build();

    mockMvc
        .perform(
            post("/api/routines/create")
                .cookie(
                    new Cookie(
                        "token",
                        jwtUtil.generateToken(userResponse.getId(), userResponse.getEmail())))
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(routineRequest)))
        .andExpect(status().isOk());
  }

  /**
   * Routine Creation Test : Invalid Token
   *
   * @throws Exception api call exception
   */
  @Test
  @DisplayName("Routine Creation Test - Invalid Token")
  public void routineCreation_invalidToken() throws Exception {
    // make routine request object
    RoutineRequest routineRequest =
        RoutineRequest.builder()
            .categoryId(routineCategoryResponseExercise.getId())
            .content("Exercise Routine")
            .build();

    // token is null
    mockMvc
        .perform(
            post("/api/routines/create")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(routineRequest)))
        .andExpect(status().isUnauthorized());

    // token is invalid
    mockMvc
        .perform(
            post("/api/routines/create")
                .cookie(new Cookie("token", "invalidToken"))
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(routineRequest)))
        .andExpect(status().isUnauthorized());
  }

  /**
   * Routine Search Test
   *
   * @throws Exception api call exception
   */
  @Test
  @DisplayName("Routine Search Test : Success")
  public void routineSearch_success() throws Exception {

    // create two routines for testing
    routineService.createRoutine(
        userResponse.getId(),
        RoutineRequest.builder()
            .categoryId(routineCategoryResponseExercise.getId())
            .content("Exercise Routine")
            .build());
    // 2
    routineService.createRoutine(
        userResponse.getId(),
        RoutineRequest.builder()
            .categoryId(routineCategoryResponseStudy.getId())
            .content("Study Routine")
            .build());

    ///  test GET API , expect size 2
    mockMvc
        .perform(get("/api/routines").cookie(userCookie))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].content").value("Exercise Routine"))
        .andExpect(jsonPath("$.length()").value(2))
        .andDo(print());
  }

  /**
   * Routine Search Test : Invalid Token
   *
   * @throws Exception api call exception
   */
  @Test
  @DisplayName("Routine Search Test : Invalid Token")
  public void routineSearch_invalidToken() throws Exception {
    // token is null
    mockMvc.perform(get("/api/routines")).andExpect(status().isUnauthorized()).andDo(print());

    // token is invalid
    mockMvc.perform(get("/api/routines")).andExpect(status().isUnauthorized());
  }
}
