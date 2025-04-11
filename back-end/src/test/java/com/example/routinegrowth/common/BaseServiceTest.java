package com.example.routinegrowth.common;

import com.example.routinegrowth.DTO.RoutineCategoryRequest;
import com.example.routinegrowth.DTO.RoutineCategoryResponse;
import com.example.routinegrowth.DTO.UserRequest;
import com.example.routinegrowth.DTO.UserResponse;
import com.example.routinegrowth.auth.jwt.JwtUtil;
import com.example.routinegrowth.service.RoutineCategoryService;
import com.example.routinegrowth.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public abstract class BaseServiceTest {

  @Autowired protected UserService userService;
  @Autowired protected RoutineCategoryService routineCategoryService;
  @Autowired protected JwtUtil jwtUtil;

  protected UserResponse userResponse;
  protected RoutineCategoryResponse routineCategoryResponseStudy;
  protected RoutineCategoryResponse routineCategoryResponseExercise;
  protected Cookie userCookie;
  protected Cookie userRefreshToken;

  @BeforeEach
  public void setUp() {
    // create user
    UserRequest userRequest = UserRequest.builder().email("User for Test").password("test").build();
    userResponse = userService.createUser(userRequest);

    // create token and save it in cookie
    userCookie =
        new Cookie(
            "accessToken",
            jwtUtil.generateToken(userResponse.getId(), userResponse.getEmail(), "access"));
    userCookie.setHttpOnly(true);
    userCookie.setPath("/");
    userCookie.setMaxAge(60 * 60); // 1 hour

    // create refresh token
    userRefreshToken = new Cookie("refreshToken", UUID.randomUUID().toString());
    userRefreshToken.setHttpOnly(true);
    userRefreshToken.setPath("/");
    userRefreshToken.setMaxAge(60 * 60 * 24 * 30); // 30 days

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
