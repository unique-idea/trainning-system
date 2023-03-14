package com.fptacademy.training.repository;

import com.fptacademy.training.domain.Class;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<Class, Long> {

    boolean existsByProgram_id(Long id);

}