package com.fptacademy.training.repository;

import com.fptacademy.training.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    /*Team 3*/
    @Query(value = "SELECT * FROM users u" +
            " INNER JOIN user_class_detail ucd " +
            " ON u.id = ucd.user_id " +
            " AND ucd.class_detail_id = ?1 " +
            " AND u.role_id = 2 ", nativeQuery = true)
    List<User> findAdminsOfClass(Long classDetailId);

   /* @Query(value = "SELECT * FROM users u" +
            " INNER JOIN user_class_detail ucd " +
            " ON u.id = ucd.user_id " +
            " AND ucd.class_detail_id = ?1 " +
            " AND u.role_id = 4", nativeQuery = true)
    List<User> findStudentsOfClass(Long classDetailId);*/
    /*Team 3*/

    boolean findUserByCodeIgnoreCase(String code);

    List<User> findByFullNameContaining (String keyword);

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

    Optional<User> findById(Long id);
}
