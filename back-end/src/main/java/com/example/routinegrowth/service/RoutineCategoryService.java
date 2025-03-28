package com.example.routinegrowth.service;

import com.example.routinegrowth.DTO.RoutineCategoryRequest;
import com.example.routinegrowth.DTO.RoutineCategoryResponse;
import com.example.routinegrowth.entity.RoutineCategory;
import com.example.routinegrowth.repository.RoutineCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoutineCategoryService {
  private final RoutineCategoryRepository routineCategoryRepository;

  public RoutineCategoryResponse createRoutineCategory(
      RoutineCategoryRequest routineCategoryRequest) {
    // routineCategoryRequest to RoutineCategory entity
    RoutineCategory routineCategory =
        RoutineCategory.builder().name(routineCategoryRequest.getName()).build();

    // save routine category
    RoutineCategory routineCategorySaved = routineCategoryRepository.save(routineCategory);

    // RoutineCategory entity to RoutineCategoryResponse
    return RoutineCategoryResponse.builder()
        .id(routineCategorySaved.getId())
        .name(routineCategorySaved.getName())
        .build();
  }
}
