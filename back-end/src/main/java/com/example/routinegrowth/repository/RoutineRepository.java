package com.example.routinegrowth.repository;

import com.example.routinegrowth.entity.Routine;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineRepository extends JpaRepository<Routine, Long> {
  List<Routine> findByUserId(Long userId);
}
