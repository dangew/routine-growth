package com.example.routinegrowth.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
@Builder
@AllArgsConstructor
public class ErrorResponse {
  @Schema(description = "에러 상태", example = "400")
  private HttpStatus status;

  @Schema(description = "에러 메시지", example = "잘못된 요청입니다.")
  private String message;
}
