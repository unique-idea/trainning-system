package com.fptacademy.training.repository;

import com.fptacademy.training.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u " +
            "WHERE (u.email LIKE %:email% OR :email IS NULL) " +
            "AND (u.fullName LIKE %:fullName% OR :fullName IS NULL) " +
            "AND (u.code LIKE %:code% OR :code IS NULL) " +
            "AND (u.level.name = :levelName OR :levelName IS NULL) " +
            "AND (u.role.name = :roleName OR :roleName IS NULL) " +
            "AND (u.activated = :activated OR :activated IS NULL) " +
            "AND (u.birthday = :birthday OR :birthday IS NULL) ")
    List<User> findByFilters(@Param("email") String email, @Param("fullName") String fullName,
            @Param("code") String code, @Param("levelName") String levelName, 
            @Param("roleName") String roleName, @Param("activated") Boolean activated, 
            @Param("birthday") LocalDate birthday);
}
