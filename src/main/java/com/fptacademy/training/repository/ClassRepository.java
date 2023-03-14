package com.fptacademy.training.repository;
import com.fptacademy.training.domain.Class;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassRepository extends JpaRepository<Class, Long> {
    List<Class> findByProgram_Id(Long id);
}
