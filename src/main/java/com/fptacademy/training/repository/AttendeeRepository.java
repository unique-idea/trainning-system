package com.fptacademy.training.repository;

import com.fptacademy.training.domain.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendeeRepository extends JpaRepository<Attendee, Long> {
}
