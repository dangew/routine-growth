package com.example.routinegrowth.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
public class UserRequest {

  @Schema(description = "유저 이메일", example = "test@test.com")
  @NotNull(groups = Create.class)
  private String email;

  @Schema(description = "유저 비밀번호", example = "test")
  @NotNull(groups = Create.class)
  private String password;

  public interface Create {}
}
