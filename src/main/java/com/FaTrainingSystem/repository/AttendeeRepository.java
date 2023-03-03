package com.FaTrainingSystem.repository;

import com.FaTrainingSystem.domain.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendeeRepository extends JpaRepository<Attendee, Integer> {}
