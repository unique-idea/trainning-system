package com.fptacademy.training.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fptacademy.training.domain.Class;

public interface ClassRepository extends JpaRepository<Class, Long> {
    List<Class> findByProgram_Id(Long id);
    boolean existsByName(String name);
    @Query("select c from Class c where c.id = :id and c.classDetail.status <> 'DELETED' " +
            "and c.classDetail.status <> 'DRAFT' and c.classDetail.status <> 'INACTIVE'")
    Optional<Class> findByIdAndStatusNotDeleted(@Param("id") Long id);
    List<Class> findByNameContainsIgnoreCaseOrCreatedBy_FullNameContainsIgnoreCase(String name, String fullName);
}
