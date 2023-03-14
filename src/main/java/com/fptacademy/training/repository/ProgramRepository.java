package com.fptacademy.training.repository;

import com.fptacademy.training.domain.Program;
import com.fptacademy.training.web.vm.ProgramVM;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    boolean existsByName(String name);
    List<Program> findByNameContainsIgnoreCaseOrCreatedBy_FullNameContainsIgnoreCase(String name, String fullName);

    boolean existsById(Long id);


    Optional<Program> findProgramById(Long id);
}
