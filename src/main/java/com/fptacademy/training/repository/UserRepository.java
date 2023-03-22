package com.fptacademy.training.repository;

import com.fptacademy.training.domain.User;

import java.util.List;
import java.util.Optional;

import com.fptacademy.training.service.dto.UserDto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
  @EntityGraph(attributePaths = {"role"})
  Optional<User> findByEmail(String email);

  @Query("Select c from User c where c.role.name = 'Trainer'")
  List<User> findAllTrainers();

  @Query("Select c from User c where c.role.name = 'Class Admin'")
  List<User> findAllClassAdmin();
}
