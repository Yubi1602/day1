package com.yo.day1.repository;


import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.yo.day1.domain.RefreshTokenSession;
import com.yo.day1.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenSessionRepository extends JpaRepository<RefreshTokenSession, Long> {

  Optional<RefreshTokenSession> findByJti(String jti);

  Optional<User> findByUser_Username(String username);

  List<RefreshTokenSession> findByUserIdAndRevokedAtIsNull(Long userId);

  List<RefreshTokenSession> findByExpiresAtBeforeAndRevokedAtIsNull(Instant now);
}
