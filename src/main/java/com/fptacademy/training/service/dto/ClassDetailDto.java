package com.fptacademy.training.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassDetailDto implements Serializable{
    private static final Long serialVersionUID = 1L;
    private Long id;
    private Integer accepted;
    private Integer actual;
    private LocalTime finish_at;
    private String others;
    private Integer planned;
    private LocalTime start_at;
    private String status;
    private ClassSimplified class_id;
    private AttendeeSimplified attendee;
    private LocationSimplified location;
    private List<UserSimplified> trainer;
    private List<UserSimplified> admin;
    private List<ScheduleSimplified> schedules;

    //list Admin

    @Getter
    @AllArgsConstructor
    public static class ClassSimplified {
        private Long id;
        private String name;
        private String code;
        private Integer duration;
        private UserSimplified created_by;
    }

    @Getter
    @AllArgsConstructor
    public static class AttendeeSimplified {
        private Long id;
        private String name;
    }

    @Getter
    @AllArgsConstructor
    public static class LocationSimplified {
        private Long id;
        private String city;

        private String fsu;
    }

    @Getter
    @AllArgsConstructor
    public static class UserSimplified {
        private Long id;
        private String name;
        private String Email;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ScheduleSimplified{
        private LocalDate study_date;
        private UserSimplified trainer;
    }

//Delivery_type
    //Program

}
