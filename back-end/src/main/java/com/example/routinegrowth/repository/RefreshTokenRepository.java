package com.example.routinegrowth.repository;

import com.example.routinegrowth.entity.RefreshToken;
import com.example.routinegrowth.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByRefreshToken(String refreshToken);

  Optional<RefreshToken> findByUser(User user);
}
