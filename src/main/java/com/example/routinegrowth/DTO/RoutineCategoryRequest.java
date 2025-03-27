package com.example.routinegrowth.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Getter @Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class RoutineCategoryRequest {
    private String name;
}
