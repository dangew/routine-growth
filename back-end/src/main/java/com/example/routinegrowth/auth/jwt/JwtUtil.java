package com.example.routinegrowth.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  @Value("${jwt.secret}")
  private String secret;

  /**
   * JWT 서명 키를 가져오는 메서드
   *
   * @return 서명 키
   */
  private SecretKey getSignedKey() {
    return Keys.hmacShaKeyFor(secret.getBytes());
  }

  /**
   * JWT 토큰에서 Claims를 가져오는 메서드
   *
   * @param token JWT 토큰
   * @return Claims
   */
  private Claims getClaimsFromToken(String token) {
    return Jwts.parser().verifyWith(getSignedKey()).build().parseSignedClaims(token).getPayload();
  }

  /**
   * JWT 토큰을 생성하는 메서드
   *
   * @param userEmail 사용자 이메일
   * @return JWT 토큰
   */
  public String generateToken(String userEmail) {
    // Generate a JWT token using the userEmail
    Key key = getSignedKey();
    long expirationMs = 3600000L; // 1 hour

    JwtBuilder builder =
        Jwts.builder()
            .subject(userEmail)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + expirationMs))
            .signWith(key);

    return builder.compact();
  }

  /**
   * JWT 토큰을 검증하는 메서드
   *
   * @param token JWT 토큰
   * @return true if the token is valid, false otherwise
   */
  public boolean validate(String token) {
    try {
      getClaimsFromToken(token); // 파싱
      return true; // 토큰이 유효한 경우 true 반환
    } catch (JwtException e) {
      return false;
    }
  }

  /**
   * JWT 토큰에서 사용자 이메일을 가져오는 메서드
   *
   * @param token JWT 토큰
   * @return 사용자 이메일
   */
  public String getUserEmail(String token) {
    return getClaimsFromToken(token).get("sub", String.class); // 토큰에서 사용자 이메일 추출
  }
}
