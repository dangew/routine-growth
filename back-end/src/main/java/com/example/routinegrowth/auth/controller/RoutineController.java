package com.example.routinegrowth.auth.controller;

import com.example.routinegrowth.DTO.RoutineRequest;
import com.example.routinegrowth.DTO.RoutineResponse;
import com.example.routinegrowth.auth.jwt.JwtUtil;
import com.example.routinegrowth.service.RoutineService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/routines")
@RequiredArgsConstructor
public class RoutineController {

  private final RoutineService routineService;
  private final JwtUtil jwtUtil;

  @PostMapping("/create")
  public ResponseEntity<RoutineResponse> createRoutine(
      @CookieValue(name = "token", required = false) String token,
      @RequestBody RoutineRequest routineRequest)
      throws Exception {
    // check if token is invalid or null
    if (token == null || !jwtUtil.validate(token)) {
      log.error("Invalid token in routine creation");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(
              RoutineResponse.builder()
                  .status(HttpStatus.UNAUTHORIZED)
                  .message("Invalid or missing token")
                  .build());
    }

    Long target_userId = jwtUtil.getUserId(token);
    log.info("userid in routine creation: {}", target_userId);

    RoutineResponse routineResponse = routineService.createRoutine(target_userId, routineRequest);

    return ResponseEntity.ok(routineResponse);
  }

  @GetMapping
  public ResponseEntity<List<RoutineResponse>> getRoutine(
      @CookieValue(name = "token", required = false) String token) throws Exception {
    // check if token is invalid or null
    if (token == null || !jwtUtil.validate(token)) {
      log.error("Invalid token in routine search");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(
              List.of(
                  RoutineResponse.builder()
                      .status(HttpStatus.UNAUTHORIZED)
                      .message("Invalid or missing token")
                      .build()));
    }

    List<RoutineResponse> routineResponses = routineService.getRoutines(token);

    return ResponseEntity.ok(routineResponses);
  }
}
