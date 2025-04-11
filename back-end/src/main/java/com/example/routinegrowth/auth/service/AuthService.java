package com.example.routinegrowth.auth.service;

import com.example.routinegrowth.auth.jwt.JwtUtil;
import com.example.routinegrowth.entity.RefreshToken;
import com.example.routinegrowth.entity.User;
import com.example.routinegrowth.repository.RefreshTokenRepository;
import com.example.routinegrowth.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final JwtUtil jwtUtil;

  /**
   * 사용자 이메일과 비밀번호를 통해 로그인을 수행하고, Access Token은 JWT로, Refresh Token은 UUID로 생성합니다.
   *
   * @param email 사용자 입력 이메일
   * @param password 사용자 입력 비밀번호
   * @return Access Token과 Refresh Token을 포함한 Map
   * @throws Exception 사용자 정보가 없거나 비밀번호가 틀린 경우
   */
  public Map<String, String> login(String email, String password) throws Exception {
    // 사용자 정보 조회
    User user_found =
        userRepository.findByEmail(email).orElseThrow(() -> new Exception("User " + "not Found"));

    // 비밀번호가 일치하는지 확인
    if (!bCryptPasswordEncoder.matches(password, user_found.getPassword())) {
      throw new Exception("Incorrect password");
    }

    // Access Token은 JWT로 생성, Refresh Token은 UUID로 생성
    String accessToken = jwtUtil.generateToken(user_found.getId(), email, "access");
    String refreshToken = UUID.randomUUID().toString();

    // Refresh Token DB에 이미 존재하는지 확인 후 있으면 삭제
    refreshTokenRepository.findByUser(user_found).ifPresent(refreshTokenRepository::delete);

    // Refresh Token은 DB에 저장
    RefreshToken refreshTokenEntity =
        RefreshToken.builder()
            .refreshToken(refreshToken)
            .user(user_found)
            .exirationDate(LocalDateTime.now().plusDays(7))
            .build();
    refreshTokenRepository.save(refreshTokenEntity);

    // 생성한 토큰들을 Map에 담아 반환
    Map<String, String> tokens = new HashMap<>();
    tokens.put("accessToken", accessToken);
    tokens.put("refreshToken", refreshToken);

    return tokens;
  }

  /**
   * 로그아웃 메서드 - 사용자가 로그아웃할 때 Refresh Token을 DB에서 삭제합니다.
   *
   * @param token 사용자가 보낸 refresh token
   */
  public void logout(String token) {
    // delete refresh token in DB
    RefreshToken refreshToken =
        refreshTokenRepository
            .findByRefreshToken(token)
            .orElseThrow(() -> new RuntimeException("Refresh token not found"));
    refreshTokenRepository.delete(refreshToken);

    // !! 클라이언트에서 쿠키를 삭제하는 방법은 클라이언트 측에서 처리해야 합니다. !!
  }

  /**
   * JWT 토큰을 통해 사용자의 이메일을 가져오는 메서드
   *
   * @param token JWT 토큰
   * @return 로그인된 사용자의 이메일
   */
  public String getUserEmail(String token) {
    return jwtUtil.getUserEmail(token);
  }
}
