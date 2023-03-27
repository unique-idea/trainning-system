package com.fptacademy.training.repository;

import com.fptacademy.training.domain.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {
    /*Team 3*/
    @Query(value = " SELECT cs.* FROM class_schedules cs " +
            " INNER JOIN class_details cd " +
            " ON cs.class_detail_id = cd.id " +
            " AND cd.status = 'OPENING' " +
            " AND cs.study_date = ?1 ", nativeQuery = true)
    List<ClassSchedule> findActiveClassByStudyDate(LocalDate date);

    @Query(value = " SELECT cs.* FROM class_schedules cs " +
            " INNER JOIN " +
            " (SELECT ucd.class_detail_id as `class_detail_id`, ucd.user_id as `user_id` " +
            " FROM user_class_detail ucd " +
            " INNER JOIN class_details cd " +
            " ON ucd.class_detail_id = cd.id " +
            " AND cd.status = 'OPENING') as t " +
            " ON cs.class_detail_id = t.class_detail_id " +
            " AND t.user_id = ?1 " +
            " AND cs.study_date = ?2 ", nativeQuery = true)
    List<ClassSchedule> findActiveClassByUserIdAndStudyDate(Long user_id, LocalDate studyDate);

    @Query(value = " SELECT cs.* FROM class_schedules cs " +
            " INNER JOIN " +
            " (SELECT ucd.class_detail_id as `class_detail_id`, ucd.user_id as `user_id` " +
            " FROM user_class_detail ucd " +
            " INNER JOIN class_details cd " +
            " ON ucd.class_detail_id = cd.id " +
            " AND cd.status = 'OPENING') as t " +
            " ON cs.class_detail_id = t.class_detail_id " +
            " AND t.user_id = ?1 " +
            " AND (cs.study_date BETWEEN ?2 AND ?3) " +
            " ORDER BY cs.study_date", nativeQuery = true)
    List<ClassSchedule> findActiveClassByUserIdAndStudyDateBetween(Long user_id, LocalDate startDate, LocalDate endDate);

    @Query(value = " SELECT cs.* FROM class_schedules cs " +
            " INNER JOIN class_details cd " +
            " ON cs.class_detail_id = cd.id " +
            " AND cd.status = 'OPENING' " +
            " AND (cs.study_date BETWEEN ?1 AND ?2) " +
            " ORDER BY cs.study_date", nativeQuery = true)
    List<ClassSchedule> findActiveClassByStudyDateBetween(LocalDate startDate, LocalDate endDate);

    @Query(value = "SELECT row_num " +
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
