package com.FaTrainingSystem.repository;

import com.FaTrainingSystem.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {}
