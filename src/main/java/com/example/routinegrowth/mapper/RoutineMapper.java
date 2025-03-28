package com.example.routinegrowth.mapper;

import com.example.routinegrowth.DTO.RoutineResponse;
import com.example.routinegrowth.entity.Routine;

public class RoutineMapper {
  public static RoutineResponse toDto(Routine routine) {
    return RoutineResponse.builder()
        .content(routine.getContent())
        .categoryName(routine.getCategory().getName())
        .build();
  }
}
