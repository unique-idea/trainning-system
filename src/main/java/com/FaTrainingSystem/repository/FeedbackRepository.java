package com.FaTrainingSystem.repository;

import com.FaTrainingSystem.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {}
