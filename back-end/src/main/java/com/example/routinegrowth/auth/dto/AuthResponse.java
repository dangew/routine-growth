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
public class AuthResponse {
  @Schema(description = "토큰", example = "123absesefsdfzefaw239hsdf")
  private String token;

  @Schema(description = "토큰 만료 시간", example = "2023-10-10T10:00:00")
  private String error;

  @Schema(description = "에러 메시지", example = "인증에 실패했습니다.")
  private String message;

  @Schema(description = "인증 요청 유저 이메일", example = "test@test.com")
  private String email;
}
