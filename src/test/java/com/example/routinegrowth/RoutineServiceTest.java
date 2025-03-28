package com.example.routinegrowth;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.routinegrowth.DTO.RoutineRequest;
import com.example.routinegrowth.DTO.RoutineResponse;
import com.example.routinegrowth.common.BaseServiceTest;
import com.example.routinegrowth.service.RoutineService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
@DisplayName("Routine Service Test")
@TestMethodOrder(MethodOrderer.Random.class)
@Transactional
public class RoutineServiceTest extends BaseServiceTest {

  @Autowired private RoutineService routineService;

  @Test
  @DisplayName("Successful Routine Creation Test")
  public void createRoutine_success() {
    // make request object for create routine
    Long userId = userResponse.getId();
    RoutineRequest routineRequest =
        new RoutineRequest(routineCategoryResponseExercise.getId(), "Morning Routine");

    try {
      // create routine
      RoutineResponse routineResponse = routineService.createRoutine(userId, routineRequest);

      // expectation
      assertThat(routineResponse).isNotNull();
      assertThat(routineResponse.getContent()).isEqualTo("Morning Routine");
      assertThat(routineResponse.getCategoryName()).isEqualTo("Exercise");

    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  @Test
  @DisplayName("Unfound User Test")
  public void createRoutine_unfoundUser() {
    // make request object for creat routine
    Long userId = userResponse.getId() + 1;
    RoutineRequest routineRequest =
        new RoutineRequest(routineCategoryResponseStudy.getId(), "user unfound routine");

    // make exception expectation
    Exception exception =
        assertThrows(Exception.class, () -> routineService.createRoutine(userId, routineRequest));

    // expectation
    assertThat(exception.getMessage()).isEqualTo("User not found");
  }

  @Test
  @DisplayName("Unfound Category Test")
  // Routine category not found
  public void createRoutine_unfoundCategory() {
    // make request object for create routine
    Long userId = userResponse.getId();
    RoutineRequest routineRequest =
        new RoutineRequest(routineCategoryResponseExercise.getId() + 1, "category unfound routine");

    // make exception expectation
    Exception exception =
        assertThrows(Exception.class, () -> routineService.createRoutine(userId, routineRequest));

    // expectation
    assertThat(exception.getMessage()).isEqualTo("Routine category not found");
  }

  /** 루틴 조회 테스트 : 성공 */
  @Test
  @DisplayName("Successful Routine Search Test")
  public void retrieveRoutine_success() throws Exception {
    // make RoutineRequest for new routine
    Long userId = userResponse.getId();
    RoutineRequest routineRequest =
        new RoutineRequest(routineCategoryResponseExercise.getId(), "Lunch Routine");

    // make routine for test
    routineService.createRoutine(userId, routineRequest);

    // search routine
    List<RoutineResponse> routineResponses = routineService.searchRoutines(userId);

    // expectation
    assertThat(routineResponses).isNotNull();
    assertThat(routineResponses.size()).isEqualTo(1);
    assertThat(routineResponses.get(0).getContent()).isEqualTo("Lunch Routine");
  }

  /** 루틴 조회 테스트 : 없는 경우 */
  @Test
  @DisplayName("No Routine Searched Test")
  public void retrieveRoutine_noRoutine() {
    // make RoutineRequest for new routine
    Long userId = userResponse.getId();

    // search routine
    List<RoutineResponse> routineResponses = routineService.searchRoutines(userId);

    // expectation
    assertThat(routineResponses).isNotNull();
    assertThat(routineResponses.size()).isEqualTo(0);
  }
}
