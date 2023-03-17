
package com.fptacademy.training.repository;
import com.fptacademy.training.domain.ClassDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ClassDetailRepository extends JpaRepository<ClassDetail,Long> {

    @Query("select c from ClassDetail c where c.classField.id = :id and c.status <> 'DELETED'")
    Optional<ClassDetail> findDetailsByClass_IdAndStatusNotDeleted(@Param("id") Long class_id);

    @Transactional
    @Modifying
    @Query("update ClassDetail c set c.status = ?1 where c.id = ?2")
    void updateStatusById(String status, Long id);



}
