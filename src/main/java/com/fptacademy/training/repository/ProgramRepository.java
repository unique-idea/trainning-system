package com.fptacademy.training.repository;


import com.fptacademy.training.domain.Program;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("NullableProblems")
public interface ProgramRepository extends JpaRepository<Program, Long> {
    @Override
    @EntityGraph(attributePaths = {"createdBy", "lastModifiedBy", "syllabuses.createdBy", "syllabuses.sessions.units"})
    Optional<Program> findById(Long id);

    @Override
    @EntityGraph(value = "graph.Program.syllabus.session")
    List<Program> findAll();

    boolean existsByName(String name);

    @EntityGraph(value = "graph.Program.syllabus.session")
    List<Program> findByName(String name);

    @EntityGraph(value = "graph.Program.syllabus.session")
    List<Program> findByNameContainsIgnoreCaseOrCreatedBy_FullNameContainsIgnoreCase(String name, String fullName);
}
