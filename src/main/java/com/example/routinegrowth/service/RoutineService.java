package com.example.routinegrowth.service;

import com.example.routinegrowth.DTO.RoutineRequest;
import com.example.routinegrowth.DTO.RoutineResponse;
import com.example.routinegrowth.entity.Routine;
import com.example.routinegrowth.entity.RoutineCategory;
import com.example.routinegrowth.entity.User;
import com.example.routinegrowth.mapper.RoutineMapper;
import com.example.routinegrowth.repository.RoutineCategoryRepository;
import com.example.routinegrowth.repository.RoutineRepository;
import com.example.routinegrowth.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoutineService {

  private final RoutineRepository routineRepository;
  private final UserRepository userRepository;
  private final RoutineCategoryRepository routineCategoryRepository;

  public RoutineResponse createRoutine(Long userId, RoutineRequest routineRequest)
      throws Exception {

    // Find user and category by id
    User user = userRepository.findById(userId).orElse(null);
    log.info("User: {}", user);
    RoutineCategory routineCategory =
        routineCategoryRepository.findById(routineRequest.getCategoryId()).orElse(null);
    log.info("Routine Category: {}", routineCategory);

    // Check if user and category exist
    if (user == null) {
      throw new Exception("User not found");
    } else if (routineCategory == null) {
      throw new Exception("Routine category not found");
    }

    // Create routine
    Routine routine =
        Routine.builder()
            .content(routineRequest.getContent())
            .user(user)
            .category(routineCategory)
            .build();

    // Save routine
    Routine routineSaved = routineRepository.save(routine);

    // Return response
    return RoutineResponse.builder()
        .content(routineRequest.getContent())
        .categoryName(routineSaved.getCategory().getName())
        .build();
  }

  /**
   * Search routine by user id
   *
   * @param userId 루틴을 조회할 사용자 id
   * @return List<RoutineResponse>
   */
  public List<RoutineResponse> searchRoutines(Long userId) {
    // get all routines by user id
    List<Routine> routines = routineRepository.findByUserId(userId);

    // convert to List<RoutineReponse> and return
    return routines.stream().map(RoutineMapper::toDto).collect(Collectors.toList());
  }
}
