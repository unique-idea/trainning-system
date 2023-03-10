package com.fptacademy.training.repository;

import com.fptacademy.training.domain.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AssessmentRepository extends JpaRepository<Assessment,Long> {
}
