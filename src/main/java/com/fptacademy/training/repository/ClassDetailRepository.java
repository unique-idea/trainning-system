
package com.fptacademy.training.repository;
import com.fptacademy.training.domain.ClassDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClassDetailRepository extends JpaRepository<ClassDetail,Long> {

    @Query(value = "Select * from class_details d where d.class_id = ? ", nativeQuery= true)
    Optional<ClassDetail> findDetailsByClass_Id(String class_id);

}
