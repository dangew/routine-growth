package com.example.routinegrowth.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UserResponse {
  @Schema(description = "유저 ID", example = "1")
  private Long id;

  @Schema(description = "유저 이메일", example = "test@test.com")
  private String email;
}
