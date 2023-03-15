package com.fptacademy.training.repository;

import com.fptacademy.training.domain.ClassDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassDetailRepository extends JpaRepository<ClassDetail, Long> {
}
