package com.fptacademy.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fptacademy.training.domain.Class;

import java.util.Optional;

public interface ClassRepository extends JpaRepository<Class,Long> {
    Optional<Class> findByProgram_Id(Long id);
    
}
