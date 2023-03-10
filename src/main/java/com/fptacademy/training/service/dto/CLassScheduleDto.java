package com.fptacademy.training.service.dto;

import java.io.Serializable;
import java.util.Date;

public class CLassScheduleDto implements Serializable{
    private static final Long serialVersionUID = 1L;
    private Long id;
    private Date study_date;
    private Integer class_detail_id;
    private Integer trainer_id;
    public CLassScheduleDto() {
    }
    public CLassScheduleDto(Long id, Date study_date, Integer class_detail_id, Integer trainer_id) {
        this.id = id;
        this.study_date = study_date;
        this.class_detail_id = class_detail_id;
        this.trainer_id = trainer_id;
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
    public Date getStudy_date() {
        return study_date;
    }
    public void setStudy_date(Date study_date) {
        this.study_date = study_date;
    }
    public Integer getClass_detail_id() {
        return class_detail_id;
    }
    public void setClass_detail_id(Integer class_detail_id) {
        this.class_detail_id = class_detail_id;
    }
    public Integer getTrainer_id() {
        return trainer_id;
    }
    public void setTrainer_id(Integer trainer_id) {
        this.trainer_id = trainer_id;
    }
    
}
