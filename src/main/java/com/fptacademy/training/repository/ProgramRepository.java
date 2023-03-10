package com.fptacademy.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fptacademy.training.domain.Program;

@Repository
public interface ProgramRepository extends JpaRepository<Program,Long> {
    
}
