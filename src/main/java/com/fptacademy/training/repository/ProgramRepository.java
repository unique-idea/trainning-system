package com.fptacademy.training.repository;

import com.fptacademy.training.domain.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    boolean existsByName(String name);
    List<Program> findByNameContainsIgnoreCaseOrCreatedBy_FullNameContainsIgnoreCase(String name, String fullName);
}
