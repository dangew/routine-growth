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
public class RoutineRequest {
  @Schema(description = "루틴의 카테고리 ID", example = "1")
  private Long categoryId;

  @Schema(description = "루틴의 제목", example = "물 마시기")
  private String content;
}
