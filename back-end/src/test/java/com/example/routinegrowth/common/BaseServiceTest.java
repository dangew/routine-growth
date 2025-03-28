package com.example.routinegrowth.common;

import com.example.routinegrowth.DTO.RoutineCategoryRequest;
import com.example.routinegrowth.DTO.RoutineCategoryResponse;
import com.example.routinegrowth.DTO.UserRequest;
import com.example.routinegrowth.DTO.UserResponse;
import com.example.routinegrowth.service.RoutineCategoryService;
import com.example.routinegrowth.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public abstract class BaseServiceTest {

  @Autowired protected UserService userService;
  @Autowired protected RoutineCategoryService routineCategoryService;

  protected UserResponse userResponse;
  protected RoutineCategoryResponse routineCategoryResponseStudy;
  protected RoutineCategoryResponse routineCategoryResponseExercise;

  @BeforeEach
  public void setUp() {
    // create user
    UserRequest userRequest = UserRequest.builder().email("User for Test").build();
    userResponse = userService.createUser(userRequest);

    // create routine category make it two for testing
    String[] categories = {"Study", "Exercise"};

    // create routine categories
    routineCategoryResponseStudy =
        routineCategoryService.createRoutineCategory(
            RoutineCategoryRequest.builder().name(categories[0]).build());
    routineCategoryResponseExercise =
        routineCategoryService.createRoutineCategory(
            RoutineCategoryRequest.builder().name(categories[1]).build());
  }
}
