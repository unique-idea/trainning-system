package com.fptacademy.training.service.dto;

import java.io.Serializable;
import java.sql.Time;

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
    private Integer class_id;
    private Integer attendee_id;
    private Integer location_id;
    public ClassDetailDto() {
    }
    public ClassDetailDto(Long id, Integer accepted, Integer actual, Time finish_at, String others, Integer planned,
            Time start_at, String status, Integer class_id, Integer attendee_id, Integer location_id) {
        this.id = id;
        this.accepted = accepted;
        this.actual = actual;
        this.finish_at = finish_at;
        this.others = others;
        this.planned = planned;
        this.start_at = start_at;
        this.status = status;
        this.class_id = class_id;
        this.attendee_id = attendee_id;
        this.location_id = location_id;
    }
    public static Long getSerialversionuid() {
        return serialVersionUID;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getAccepted() {
        return accepted;
    }
    public void setAccepted(Integer accepted) {
        this.accepted = accepted;
    }
    public Integer getActual() {
        return actual;
    }
    public void setActual(Integer actual) {
        this.actual = actual;
    }
    public Time getFinish_at() {
        return finish_at;
    }
    public void setFinish_at(Time finish_at) {
        this.finish_at = finish_at;
    }
    public String getOthers() {
        return others;
    }
    public void setOthers(String others) {
        this.others = others;
    }
    public Integer getPlanned() {
        return planned;
    }
    public void setPlanned(Integer planned) {
        this.planned = planned;
    }
    public Time getStart_at() {
        return start_at;
    }
    public void setStart_at(Time start_at) {
        this.start_at = start_at;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Integer getClass_id() {
        return class_id;
    }
    public void setClass_id(Integer class_id) {
        this.class_id = class_id;
    }
    public Integer getAttendee_id() {
        return attendee_id;
    }
    public void setAttendee_id(Integer attendee_id) {
        this.attendee_id = attendee_id;
    }
    public Integer getLocation_id() {
        return location_id;
    }
    public void setLocation_id(Integer location_id) {
        this.location_id = location_id;
    }
    
}
