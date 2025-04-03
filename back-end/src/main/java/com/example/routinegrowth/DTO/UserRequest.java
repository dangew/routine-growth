package com.example.routinegrowth.DTO;

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

  @NotNull(groups = Create.class)
  private String email;

  @NotNull(groups = Create.class)
  private String password;

  public interface Create {}
}
