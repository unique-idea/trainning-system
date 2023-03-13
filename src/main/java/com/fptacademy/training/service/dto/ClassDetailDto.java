package com.fptacademy.training.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Time;
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
    private Time finish_at;
    private String others;
    private Integer planned;
    private Time start_at;
    private String status;
    private ClassSimplified class_id;
    private AttendeeSimplified attendee;
    private LocationSimplified location;
    private List<Trainer> trainer;

    @Getter
    @AllArgsConstructor
    public static class ClassSimplified {
        private Long id;
        private String name;
        private String code;
        private Integer duration;
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
    public static class Trainer {
        private Long id;
        private String name;
    }

//    public ClassDetailDto() {
//    }
//    public ClassDetailDto(Long id, Integer accepted, Integer actual, Time finish_at, String others, Integer planned,
//                          Time start_at, String status, Long class_id, Long attendee_id, Long location_id) {
//        this.id = id;
//        this.accepted = accepted;
//        this.actual = actual;
//        this.finish_at = finish_at;
//        this.others = others;
//        this.planned = planned;
//        this.start_at = start_at;
//        this.status = status;
//        this.class_id = class_id;
//        this.attendee_id = attendee_id;
//        this.location_id = location_id;
//    }
//    public static Long getSerialversionuid() {
//        return serialVersionUID;
//    }
//    public Long getId() {
//        return id;
//    }
//    public void setId(Long id) {
//        this.id = id;
//    }
//    public Integer getAccepted() {
//        return accepted;
//    }
//    public void setAccepted(Integer accepted) {
//        this.accepted = accepted;
//    }
//    public Integer getActual() {
//        return actual;
//    }
//    public void setActual(Integer actual) {
//        this.actual = actual;
//    }
//    public Time getFinish_at() {
//        return finish_at;
//    }
//    public void setFinish_at(Time finish_at) {
//        this.finish_at = finish_at;
//    }
//    public String getOthers() {
//        return others;
//    }
//    public void setOthers(String others) {
//        this.others = others;
//    }
//    public Integer getPlanned() {
//        return planned;
//    }
//    public void setPlanned(Integer planned) {
//        this.planned = planned;
//    }
//    public Time getStart_at() {
//        return start_at;
//    }
//    public void setStart_at(Time start_at) {
//        this.start_at = start_at;
//    }
//    public String getStatus() {
//        return status;
//    }
//    public void setStatus(String status) {
//        this.status = status;
//    }
//    public Long getClass_id() {
//        return class_id;
//    }
//    public void setClass_id(Long class_id) {
//        this.class_id = class_id;
//    }
//    public Long getAttendee_id() {
//        return attendee_id;
//    }
//    public void setAttendee_id(Long attendee_id) {
//        this.attendee_id = attendee_id;
//    }
//    public Long getLocation_id() {
//        return location_id;
//    }
//    public void setLocation_id(Long location_id) {
//        this.location_id = location_id;
//    }

}
