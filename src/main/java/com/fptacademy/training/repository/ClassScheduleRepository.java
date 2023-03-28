package com.fptacademy.training.repository;

import com.fptacademy.training.domain.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {
    /*Team 3*/
    @Query(value = " SELECT cs.* FROM class_schedules cs " +
            " INNER JOIN class_details cd " +
            " ON cs.class_detail_id = cd.id " +
            " AND cd.status = ?2 " +
            " AND cs.study_date = ?1 ", nativeQuery = true)
    List<ClassSchedule> findActiveClassByStudyDate(LocalDate date, String status);

    @Query(value = " SELECT cs FROM ClassDetail cd " +
            " JOIN cd.schedules cs " +
            " JOIN cd.users u " +
            " WHERE cd.status = :status " +
            " AND cs.studyDate = :date " +
            " AND (:userId IS NULL OR u.id = :userId) " +
            " AND (:className IS NULL OR cd.classField.name = :className) " +
            " AND (:classCode IS NULL OR cd.classField.code = :classCode) " +
            " AND (:city IS NULL OR cd.location.city = :city) ")
    List<ClassSchedule> findFilterActiveClassByStudyDate(
            @Param("date") LocalDate date,
            @Param("status") String status,
            @Param("userId") Long userId,
            @Param("className") String className,
            @Param("classCode") String classCode,
            @Param("city") String city
    );

    @Query(value = " SELECT cs FROM ClassDetail cd " +
            " JOIN cd.schedules cs " +
            " JOIN cd.users u " +
            " WHERE cd.status = :status " +
            " AND cs.studyDate BETWEEN :startDate AND :endDate " +
            " AND (:userId IS NULL OR u.id = :userId) " +
            " AND (:className IS NULL OR cd.classField.name = :className) " +
            " AND (:classCode IS NULL OR cd.classField.code = :classCode) " +
            " AND (:city IS NULL OR cd.location.city = :city) ")
    List<ClassSchedule> findFilterActiveClassByStudyDateBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("status") String status,
            @Param("userId") Long userId,
            @Param("className") String className,
            @Param("classCode") String classCode,
            @Param("city") String city
    );

    @Query(value = " SELECT cs.* FROM class_schedules cs " +
            " INNER JOIN " +
            " (SELECT ucd.class_detail_id as `class_detail_id`, ucd.user_id as `user_id` " +
            " FROM user_class_detail ucd " +
            " INNER JOIN class_details cd " +
            " ON ucd.class_detail_id = cd.id " +
            " AND cd.status = ?3) as t " +
            " ON cs.class_detail_id = t.class_detail_id " +
            " AND t.user_id = ?1 " +
            " AND cs.study_date = ?2 ", nativeQuery = true)
    List<ClassSchedule> findActiveClassByUserIdAndStudyDate(Long userId, LocalDate studyDate, String status);

    @Query(value = " SELECT cs.* FROM class_schedules cs " +
            " INNER JOIN " +
            " (SELECT ucd.class_detail_id as `class_detail_id`, ucd.user_id as `user_id` " +
            " FROM user_class_detail ucd " +
            " INNER JOIN class_details cd " +
            " ON ucd.class_detail_id = cd.id " +
            " AND cd.status = ?4) as t " +
            " ON cs.class_detail_id = t.class_detail_id " +
            " AND t.user_id = ?1 " +
            " AND (cs.study_date BETWEEN ?2 AND ?3) " +
            " ORDER BY cs.study_date", nativeQuery = true)
    List<ClassSchedule> findActiveClassByUserIdAndStudyDateBetween(Long userId, LocalDate startDate, LocalDate endDate, String status);


    @Query(value = " SELECT cs.* FROM class_schedules cs " +
            " INNER JOIN class_details cd " +
            " ON cs.class_detail_id = cd.id " +
            " AND cd.status = ?3 " +
            " AND (cs.study_date BETWEEN ?1 AND ?2) " +
            " ORDER BY cs.study_date", nativeQuery = true)
    List<ClassSchedule> findActiveClassByStudyDateBetween(LocalDate startDate, LocalDate endDate, String status);

    @Query(value = "SELECT COALESCE(MAX(row_num), 0) " +
            " FROM ( " +
            "    SELECT *, ROW_NUMBER() OVER (ORDER BY study_date) as row_num " +
            "    FROM class_schedules " +
            "    WHERE class_detail_id = ?1 " +
            "    ORDER BY study_date " +
            ") t " +
            " WHERE t.id = ?2 ", nativeQuery = true)
    Integer getCurrentClassDayOfClassSchedule(Long classDetailId, Long classScheduleId);

    /*Team 3*/
}
