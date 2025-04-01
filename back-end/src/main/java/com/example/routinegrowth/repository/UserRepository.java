package com.example.routinegrowth.repository;

import com.example.routinegrowth.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  /**
   * Find user by email
   *
   * @param email email of the user
   * @return Optional<User>
   */
  Optional<User> findByEmail(String email);
}
