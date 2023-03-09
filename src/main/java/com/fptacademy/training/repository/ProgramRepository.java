package com.fptacademy.training.repository;

import com.fptacademy.training.domain.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    boolean existsByName(String name);
}
