package com.example.routinegrowth;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.routinegrowth.auth.jwt.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@DisplayName("JWT Util Test")
@TestMethodOrder(MethodOrderer.Random.class)
@Transactional
public class JwtUtilTest {
  @Autowired private JwtUtil jwtUtil;

  @Value("${jwt.secret}")
  private String secretKey;

  /** JWT Token Generation Test */
  @Test
  @DisplayName("JWT Token Generation Test")
  public void generateTokenTeset() {
    Long userId = 1L;
    String userEmail = "JWT@TEST";
    String token = jwtUtil.generateToken(userId, userEmail);

    assertThat(jwtUtil.validate(token)).isTrue();
    assertThat(jwtUtil.getUserEmail(token)).isEqualTo(userEmail);
  }

  /** Expired JWT Token Validation Test */
  @Test
  @DisplayName("Expired Token Validation Test")
  public void validateExpiredTokenTest() {
    String userEmail = "expired@token";
    String token =
        Jwts.builder()
            .subject(userEmail)
            .issuedAt(new Date(System.currentTimeMillis() - 3600000L))
            .expiration(new Date(System.currentTimeMillis() - 1000L))
            .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
            .compact();

    assertThat(jwtUtil.validate(token)).isFalse();
  }

  /** Unvalid JWT Token Validation Test */
  @Test
  @DisplayName("Unvalid Token Validation Test")
  public void unvalidTokenTest() {
    String userEmail = "unvalid@token";
    String token =
        Jwts.builder()
            .subject(userEmail)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 3600000L))
            .signWith(Keys.hmacShaKeyFor("dskhfsfeihfsefskeuhskeuagkuefhskefh".getBytes()))
            .compact();

    assertThat(jwtUtil.validate(token)).isFalse();
  }

  /** Wrong Form Token Validation Test */
  @Test
  @DisplayName("Wrong Form Token Validation Test")
  public void wrongFormTokenTest() {
    String wrongToken = "wrong.form.token";

    assertThat(jwtUtil.validate(wrongToken)).isFalse();
  }
}
