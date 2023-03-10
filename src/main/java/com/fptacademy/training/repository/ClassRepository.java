package com.fptacademy.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fptacademy.training.domain.Class;

public interface ClassRepository extends JpaRepository<Class,Long> {
    
}
