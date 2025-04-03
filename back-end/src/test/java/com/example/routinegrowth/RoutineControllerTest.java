package com.example.routinegrowth;

import com.example.routinegrowth.DTO.RoutineRequest;
import com.example.routinegrowth.common.BaseServiceTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@DisplayName("Routine Controller Test")
@TestMethodOrder(MethodOrderer.Random.class)
@AutoConfigureMockMvc
@Transactional
public class RoutineControllerTest extends BaseServiceTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @DisplayName("Routine Creation Test")
  public void routineCreation_success() throws Exception {
    // make routine request object
    RoutineRequest routineRequest =
      RoutineRequest.builder().categoryId(routineCategoryResponseStudy.getId())
        .content("Study " + "Routine").build();

    mockMvc.perform(
      post("/api/routines/create")
        .contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(routineRequest))
    )
      .andExpect(status().isOk());

  }
}
