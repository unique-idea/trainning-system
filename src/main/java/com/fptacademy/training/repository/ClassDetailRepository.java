
package com.fptacademy.training.repository;
import com.fptacademy.training.domain.ClassDetail;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ClassDetailRepository extends JpaRepository<ClassDetail,Long> {
    
}
