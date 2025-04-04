package com.example.routinegrowth.DTO;

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
  private String content;
  private String categoryName;
  private HttpStatus status;
  private String message;
}
