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
            " AND cd.status = ?2 " +
            " AND cs.study_date = ?1 ", nativeQuery = true)
    List<ClassSchedule> findActiveClassByStudyDate(LocalDate date, String status);

//    @Query(value = " SELECT cs FROM ClassSchedule cs " +
//            " JOIN ClassDetail cd " +
//            " WHERE cd.status = :status " +
//            " AND cs.studyDate = :date ")
//    List<ClassSchedule> findActiveClassByStudyDate1(
//            @Param("date") LocalDate date,
//            @Param("status") String status);

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
    List<ClassSchedule> findActiveClassByUserIdAndStudyDate(Long user_id, LocalDate studyDate, String status);

//    @Query(value = " SELECT cs FROM ClassSchedule cs " +
//            " JOIN (SELECT cd FROM ClassDetail cd " +
//            " JOIN User u " +
//            " WHERE cd.status = :status " +
//            " AND u.id = :userId) " +
//            " WHERE cs.studyDate = :studyDate")
//    List<ClassSchedule> findActiveClassByUserIdAndStudyDate1(@Param("userId") Long user_id,
//                                                             @Param("studyDate") LocalDate studyDate,
//                                                             @Param("status") String status);

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
    List<ClassSchedule> findActiveClassByUserIdAndStudyDateBetween(Long user_id, LocalDate startDate, LocalDate endDate, String status);

//    @Query(value = " SELECT cs FROM ClassSchedule cs " +
//            " JOIN (SELECT cd FROM ClassDetail cd " +
//            " JOIN User u " +
//            " WHERE cd.status = :status " +
//            " AND u.id = :userId) " +
//            " WHERE (cs.studyDate BETWEEN :startDate AND :endDate) " +
//            " ORDER BY cs.studyDate")
//    List<ClassSchedule> findActiveClassByUserIdAndStudyDateBetween1(
//            @Param("userId") Long user_id,
//            @Param("startDate") LocalDate startDate,
//            @Param("endDate") LocalDate endDate,
//            @Param("status") String status);

    @Query(value = " SELECT cs.* FROM class_schedules cs " +
            " INNER JOIN class_details cd " +
            " ON cs.class_detail_id = cd.id " +
            " AND cd.status = ?3 " +
            " AND (cs.study_date BETWEEN ?1 AND ?2) " +
            " ORDER BY cs.study_date", nativeQuery = true)
    List<ClassSchedule> findActiveClassByStudyDateBetween(LocalDate startDate, LocalDate endDate, String status);

//    @Query(value = " SELECT cs FROM ClassSchedule cs " +
//            " JOIN ClassDetail cd " +
//            " WHERE cd.status = :status " +
//            " AND (cs.studyDate BETWEEN :startDate AND :endDate) " +
//            " ORDER BY cs.studyDate")
//    List<ClassSchedule> findActiveClassByStudyDateBetween1(
//            @Param("startDate") LocalDate startDate,
//            @Param("endDate") LocalDate endDate,
//            @Param("status") String status);

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
