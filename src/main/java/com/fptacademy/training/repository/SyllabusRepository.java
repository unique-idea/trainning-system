package com.fptacademy.training.repository;

import com.fptacademy.training.domain.Syllabus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SyllabusRepository extends JpaRepository<Syllabus, Long> {
}
