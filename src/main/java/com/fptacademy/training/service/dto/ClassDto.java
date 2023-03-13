package com.fptacademy.training.service.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassDto implements Serializable{
    private static final Long serialVersionUID = 1L;
    private Long id;
    private Instant created_at;
    private String code;
    private Integer duration;
    private String name;
    private Creator created_by;
    private AttendeeSimplified  attendee;
    private LocationSimplified location_id;

    @Getter
    @AllArgsConstructor
    public static class Creator {
        private Long id;
        private String name;
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
}
