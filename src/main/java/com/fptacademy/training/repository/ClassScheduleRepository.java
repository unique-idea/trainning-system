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
            " AND (:city IS NULL OR cd.location.city = :city) " +
            " ORDER BY cs.studyDate ")
    List<ClassSchedule> findFilterActiveClassByStudyDateBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("status") String status,
            @Param("userId") Long userId,
            @Param("className") String className,
            @Param("classCode") String classCode,
            @Param("city") String city
    );

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
