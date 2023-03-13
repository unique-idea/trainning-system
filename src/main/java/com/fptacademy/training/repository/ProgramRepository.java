package com.fptacademy.training.repository;

<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fptacademy.training.domain.Program;

@Repository
public interface ProgramRepository extends JpaRepository<Program,Long> {
    
=======
import com.fptacademy.training.domain.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    boolean existsByName(String name);
    List<Program> findByNameContainsIgnoreCaseOrCreatedBy_FullNameContainsIgnoreCase(String name, String fullName);
>>>>>>> origin/master
}
