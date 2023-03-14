package com.fptacademy.training.repository;

import com.fptacademy.training.domain.FormatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormatTypeRepository extends JpaRepository<FormatType, Long> {
    List<FormatType> findFormatTypeByNameContains(String name);
}