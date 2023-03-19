package com.fptacademy.training.repository;

import com.fptacademy.training.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
