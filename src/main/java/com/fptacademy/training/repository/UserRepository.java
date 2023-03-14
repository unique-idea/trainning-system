package com.fptacademy.training.repository;

import com.fptacademy.training.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    List<User> findByFullNameContaining (String keyword);

    Optional<User> findByFullName (String name);
}
