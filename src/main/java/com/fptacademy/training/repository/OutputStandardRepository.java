package com.fptacademy.training.repository;

import com.fptacademy.training.domain.OutputStandard;
import com.fptacademy.training.domain.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OutputStandardRepository extends JpaRepository<OutputStandard, Long> {
    // Query exact word by word
    @Query("select o from OutputStandard o where o.name = ?1")
    Optional<OutputStandard> findByName(String name);

    // Query some name likes the word
    @Query("select o from OutputStandard o where o.name like %?1%")
    List<OutputStandard> findByNameContains(String name);
}
