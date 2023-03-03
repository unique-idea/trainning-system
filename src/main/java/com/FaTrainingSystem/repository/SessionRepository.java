package com.FaTrainingSystem.repository;

import com.FaTrainingSystem.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {}
