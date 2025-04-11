package com.example.routinegrowth.auth.controller;

import com.example.routinegrowth.DTO.ErrorResponse;
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
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
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
                schema = @Schema(implementation = ErrorResponse.class),
                examples =
                    @ExampleObject(
                        name = "login_fail",
                        summary = "Login failure",
                        value =
                            "{\"message\": \"Invalid email"
                                + " or password\", \"status\": \"401\"}")))
  })
  @PostMapping("/login")
  public ResponseEntity<?> login(
      @RequestBody AuthRequest authRequest, HttpServletResponse response) {
    try {
      Map<String, String> result =
          authService.login(authRequest.getEmail(), authRequest.getPassword());

      String accessToken = result.get("accessToken");
      Cookie accessCookie = new Cookie("accessToken", accessToken);
      accessCookie.setPath("/");
      accessCookie.setHttpOnly(true);
      accessCookie.setMaxAge(60 * 15); // 15분
      response.addCookie(accessCookie);

      String refreshToken = result.get("refreshToken");
      Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
      refreshCookie.setPath("/");
      refreshCookie.setHttpOnly(true);
      refreshCookie.setMaxAge(60 * 60 * 24 * 7); // 일주일
      response.addCookie(refreshCookie);

      // refresh token

      return ResponseEntity.ok(
          AuthResponse.builder().token(accessToken).email(authRequest.getEmail()).build());
    } catch (Exception e) {
      return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
          .body(
              ErrorResponse.builder()
                  .status(HttpStatus.UNAUTHORIZED)
                  .message(e.getMessage())
                  .build());
    }
  }

  /**
   * 토큰에서 로그인된 사용자 정보를 가져오는 메서드
   *
   * @param token JWT 토큰
   * @return ResponseEntity
   */
  @GetMapping
  public ResponseEntity<?> getLoggedInUser(@CookieValue(name = "accessToken") String token) {
    try {
      String loggedInUserEmail = authService.getUserEmail(token);
      return ResponseEntity.ok(
          AuthResponse.builder().email(loggedInUserEmail).message("User logged in").build());

    } catch (Exception e) {
      return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
          .body(
              ErrorResponse.builder()
                  .status(HttpStatus.UNAUTHORIZED)
                  .message(e.getMessage())
                  .build());
    }
  }

  @GetMapping("/logout")
  public ResponseEntity<?> logout(
      HttpServletResponse response, @CookieValue(name = "refreshToken") String token) {
    try {
      authService.logout(token);

      Cookie accessCookie = new Cookie("accessToken", null);
      accessCookie.setPath("/");
      accessCookie.setMaxAge(0); // 쿠키 만료 시키기
      response.addCookie(accessCookie);

      Cookie refreshCookie = new Cookie("refreshToken", null);
      refreshCookie.setPath("/");
      refreshCookie.setMaxAge(0); // 쿠키 말료 시키기
      response.addCookie(refreshCookie);

      return ResponseEntity.ok("Logout successful");
    } catch (Exception e) {
      return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
          .body(
              ErrorResponse.builder()
                  .status(HttpStatus.UNAUTHORIZED)
                  .message(e.getMessage())
                  .build());
    }
  }
}
