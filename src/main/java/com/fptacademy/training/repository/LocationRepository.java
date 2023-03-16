package com.fptacademy.training.repository;

import com.fptacademy.training.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

}
