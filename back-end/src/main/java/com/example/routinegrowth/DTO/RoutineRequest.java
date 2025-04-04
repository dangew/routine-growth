package com.example.routinegrowth.DTO;

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
  private Long categoryId;
  private String content;
}
