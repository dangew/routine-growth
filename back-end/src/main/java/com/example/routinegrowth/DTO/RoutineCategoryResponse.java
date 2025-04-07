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
@AllArgsConstructor
@RequiredArgsConstructor
public class RoutineCategoryResponse {
  @Schema(description = "루틴 카테고리 ID", example = "1")
  private Long id;

  @Schema(description = "루틴 카테고리 이름", example = "운동")
  private String name;
}
