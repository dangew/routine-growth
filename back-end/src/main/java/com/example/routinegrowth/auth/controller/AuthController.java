package com.example.routinegrowth.auth.controller;

import com.example.routinegrowth.auth.dto.AuthRequest;
import com.example.routinegrowth.auth.dto.AuthResponse;
import com.example.routinegrowth.auth.service.AuthService;
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
      return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(e.getMessage());
    }
  }
}
