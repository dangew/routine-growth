package com.example.routinegrowth.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class RoutineResponse {
  @Schema(description = "루틴 내용", example = "물 마시기")
  private String content;

  @Schema(description = "루틴 카테고리 ID", example = "1")
  private String categoryName;

  @Schema(description = "응담 상태", example = "200")
  private HttpStatus status;

  @Schema(description = "응답 메세지", example = "루틴 생성 성공")
  private String message;
}
