package com.example.routinegrowth.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

  @NotBlank(message = "이메일은 비어있을 수 없습니다.")
  @Email(message = "이메일 형식이 아닙니다.")
  @Schema(description = "유저 이메일", example = "test@test.com")
  @NotNull(groups = Create.class)
  private String email;

  @NotBlank(message = "비밀번호는 비어있을 수 없습니다.")
  @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
  @Schema(description = "유저 비밀번호", example = "test")
  @NotNull(groups = Create.class)
  private String password;

  public interface Create {}
}
