package com.fptacademy.training.repository;


import com.fptacademy.training.domain.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    boolean existsByName(String name);
    List<Program> findByName(String name);
    List<Program> findByNameContainsIgnoreCaseOrCreatedBy_FullNameContainsIgnoreCase(String name, String fullName);

}
