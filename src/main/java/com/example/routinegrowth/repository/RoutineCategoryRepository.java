package com.example.routinegrowth.repository;

import com.example.routinegrowth.entity.RoutineCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineCategoryRepository extends JpaRepository<RoutineCategory, Long> {
}
