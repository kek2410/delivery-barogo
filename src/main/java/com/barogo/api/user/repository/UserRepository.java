package com.barogo.api.user.repository;

import com.barogo.api.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByUserId(String userId);
  Optional<User> findByUserId(String userId);


}
