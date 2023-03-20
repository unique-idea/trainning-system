package com.fptacademy.training.repository;

import com.fptacademy.training.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean findUserByCodeIgnoreCase(String code);

    boolean existsByCode(String code);

    Page<User> findUserByActivatedIsTrue(Pageable pageable);

    Optional<User> findByEmail(String email);

    List<User> findByFullNameContaining(String keyword);

    @Query("Select c from User c where c.role.name = 'Trainer'")
    List<User> findAllTrainers();

    @Query("Select c from User c where c.role.name = 'Class Admin'")
    List<User> findAllClassAdmin();

    /*Team 3*/
    //no use
    /*@Query(value = "SELECT u.* FROM users u " +
            " INNER JOIN user_class_detail ucd " +
            " ON u.id = ucd.user_id " +
            " AND u.role_id = 2 " +
            " AND ucd.class_detail_id = ?1 ", nativeQuery = true)
    List<User> findAdminsOfClass(Long classDetailId);*/

    //no use
    /*@Query(value = "SELECT u.* FROM users u " +
            " INNER JOIN user_class_detail ucd " +
            " ON u.id = ucd.user_id " +
            " AND u.role_id = 3 " +
            " AND ucd.class_detail_id = ?1 ", nativeQuery = true)
    List<User> findTrainerOfClass(Long classDetailId);*/

    /*Team 3*/

    @Query("SELECT u FROM User u " +
            "WHERE (:emailParam IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :emailParam, '%'))) " +
            "AND (:fullNameParam IS NULL OR LOWER(u.fullName) LIKE LOWER(CONCAT('%', :fullNameParam, '%'))) " +
            "AND (:codeParam IS NULL OR LOWER(u.code) LIKE LOWER(CONCAT('%', :codeParam, '%'))) " +
            "AND (:levelNameParam IS NULL OR LOWER(u.level.name) LIKE LOWER(CONCAT('%', :levelNameParam, '%'))) " +
            "AND (:roleNameParam IS NULL OR LOWER(u.role.name) LIKE LOWER(CONCAT('%', :roleNameParam, '%'))) " +
            "AND (:activatedParam IS NULL OR u.activated = :activatedParam) " +
            "AND (:birthdayFromParam IS NULL OR :birthdayToParam IS NULL OR (:birthdayFromParam <= u.birthday AND u.birthday <= :birthdayToParam)) " +
            "AND (:statusParam IS NULL OR LOWER(u.status) LIKE LOWER(CONCAT('%', :statusParam, '%')))")
    Page<User> findByFilters(@Param("emailParam") String email, @Param("fullNameParam") String fullName,
                             @Param("codeParam") String code, @Param("levelNameParam") String levelName,
                             @Param("roleNameParam") String roleName, @Param("activatedParam") Boolean activated,
                             @Param("birthdayFromParam") LocalDate birthdayFrom, @Param("birthdayToParam") LocalDate birthdayTo,
                             @Param("statusParam") String status, Pageable pageable);
}