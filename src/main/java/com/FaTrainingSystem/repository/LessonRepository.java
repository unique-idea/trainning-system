package com.FaTrainingSystem.repository;

import com.FaTrainingSystem.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {}
