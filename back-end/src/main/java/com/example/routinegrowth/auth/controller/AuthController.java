package com.example.routinegrowth.auth.controller;

import com.example.routinegrowth.auth.dto.AuthRequest;
import com.example.routinegrowth.auth.dto.AuthResponse;
import com.example.routinegrowth.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  /**
   * Handles user login by validating credentials and generating a JWT token.
   *
   * @param authRequest the authentication request containing email and password
   * @param response the HTTP response to set the token cookie
   * @return a ResponseEntity containing the authentication response or an error message
   */
  @Operation(
      summary = "User Login",
      description = "Handles user login by validating credentials and generating a JWT token.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Login successful",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AuthResponse.class),
                examples =
                    @ExampleObject(
                        name = "login_success",
                        summary = "Login success",
                        value = "{\"token\": \"your_jwt_token_here\"}"))),
    @ApiResponse(
        responseCode = "401",
        description = "Unauthorized",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AuthResponse.class),
                examples =
                    @ExampleObject(
                        name = "login_fail",
                        summary = "Login failure",
                        value =
                            "{\"message\": \"Invalid email"
                                + " or password\", \"email\": \"test@test.com\"}")))
  })
  @PostMapping("/login")
  public ResponseEntity<?> login(
      @RequestBody AuthRequest authRequest, HttpServletResponse response) {
    try {
      String token = authService.login(authRequest.getEmail(), authRequest.getPassword());
      Cookie cookie = new Cookie("token", token);
      cookie.setPath("/");
      cookie.setHttpOnly(true);
      response.addCookie(cookie);

      return ResponseEntity.ok(AuthResponse.builder().token(token).build());
    } catch (Exception e) {
      return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
          .body(
              AuthResponse.builder().message(e.getMessage()).email(authRequest.getEmail()).build());
    }
  }
}
