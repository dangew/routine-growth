package com.example.routinegrowth.controller;

import com.example.routinegrowth.DTO.RoutineRequest;
import com.example.routinegrowth.DTO.RoutineResponse;
import com.example.routinegrowth.auth.jwt.JwtUtil;
import com.example.routinegrowth.service.RoutineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

  /**
   * Creates a new routine for the logged-in user.
   *
   * @param token JWT token for authentication and verification of user identity
   * @param routineRequest Request object containing routine details
   * @return ResponseEntity containing the created routine details or an error message
   * @throws Exception If the routine creation fails
   */
  @Operation(
      summary = "Create Routine",
      description = "Create a new routine for the logged-in user. Requires a valid JWT token.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Routine created successfully",
        content =
            @Content(
                mediaType = "application/json",
                examples =
                    @ExampleObject(
                        name = "routineCreation_success",
                        summary = "Routine creation success",
                        value =
                            "{\"content\": \"Routine 1\", \"categoryName\": \"Category 1\", \"status\": \"200\", \"message\": \"Routine created successfully\"}"),
                schema = @Schema(implementation = RoutineResponse.class))),
    @ApiResponse(
        responseCode = "401",
        description = "Unauthorized",
        content =
            @Content(
                mediaType = "application/json",
                examples =
                    @ExampleObject(
                        name = "unauthorizedError",
                        summary = "Authentication error",
                        value =
                            "{\"status\": \"UNAUTHORIZED\", \"message\": \"Invalid or missing token\"}"),
                schema = @Schema(implementation = RoutineResponse.class))),
  })
  @PostMapping("/create")
  public ResponseEntity<RoutineResponse> createRoutine(
      @CookieValue(name = "accessToken", required = false) String token,
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

  /**
   * Find routines by user id.
   *
   * @param token JWT token for authentication and verification of user identity
   * @return ResponseEntity containing a list of routines or an error message
   */
  @Operation(
      summary = "Get Routines by User",
      description = "Get all routines for the logged-in user. Requires a valid JWT token.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Routines retrieved successfully",
        content =
            @Content(
                mediaType = "application/json",
                examples =
                    @ExampleObject(
                        name = "routineList",
                        summary = "List of routines",
                        value =
                            "[{\"content\": \"Routine 1\", \"categoryName\": \"Category 1\"}, {\"content\": \"Routine 2\", \"categoryName\": \"Category 2\"}]"),
                array = @ArraySchema(schema = @Schema(implementation = RoutineResponse.class)))),
    @ApiResponse(
        responseCode = "401",
        description = "Unauthorized",
        content =
            @Content(
                mediaType = "application/json",
                examples =
                    @ExampleObject(
                        name = "unauthorizedError",
                        summary = "Invalid token",
                        value =
                            "[{\"status\": \"UNAUTHORIZED\", \"message\": \"Invalid or missing token\"}]"),
                array = @ArraySchema(schema = @Schema(implementation = RoutineResponse.class))))
  })
  @GetMapping
  public ResponseEntity<List<RoutineResponse>> getRoutine(
      @CookieValue(name = "accessToken", required = false) String token) {
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
