package com.fptacademy.training.repository;

import com.fptacademy.training.domain.OutputStandard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutputStandardRepository extends JpaRepository<OutputStandard, Long> {}
