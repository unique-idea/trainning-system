package com.fptacademy.training.repository;

import com.fptacademy.training.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);


    @Modifying
    @Query("UPDATE Role r SET r.permissions = :permission WHERE r.name = :role")
    void updatePermission(@Param("role") String role,@Param("permission") List<String> permission);
}
