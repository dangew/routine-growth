package com.example.routinegrowth.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AuthRequest {
  @Schema(description = "인증 요청 받는 유저의 이메일", example = "test@test.com")
  private String email;

  @Schema(description = "인증 요청 받는 유저의 비밀번호", example = "test")
  private String password;
}
