package com.example.routinegrowth.auth.service;

import com.example.routinegrowth.auth.jwt.JwtUtil;
import com.example.routinegrowth.entity.User;
import com.example.routinegrowth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final JwtUtil jwtUtil;

  /**
   * @param email 사용자 입력 이메일
   * @param password 사용자 입력 비밀번호
   * @return JWT 토큰
   * @throws Exception 사용자 정보가 없거나 비밀번호가 틀린 경우
   */
  public String login(String email, String password) throws Exception {
    User user_found =
        userRepository.findByEmail(email).orElseThrow(() -> new Exception("User " + "not Found"));

    if (!bCryptPasswordEncoder.matches(password, user_found.getPassword())) {
      throw new Exception("Incorrect password");
    }

    return jwtUtil.generateToken(user_found.getId(), email);
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
