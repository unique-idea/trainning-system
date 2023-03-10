package com.fptacademy.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fptacademy.training.domain.ClassSchedule;

public interface ClassScheduleRepository extends JpaRepository<ClassSchedule,Long>{
    
}
