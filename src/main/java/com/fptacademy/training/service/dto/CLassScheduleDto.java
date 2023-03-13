package com.fptacademy.training.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CLassScheduleDto implements Serializable{
    private static final Long serialVersionUID = 1L;
    private Long id;
    private Date study_date;
    private Detail class_detail_id;
    private Trainer trainer_id;

    @Getter
    @AllArgsConstructor
    public static class Trainer {
        private Long id;
        private String name;
    }

    @Getter
    @AllArgsConstructor
    public static class Detail {
        private Long id;
        private String name;
    }
//    public CLassScheduleDto() {
//    }
//    public CLassScheduleDto(Long id, Date study_date, Long class_detail_id, Long trainer_id) {
//        this.id = id;
//        this.study_date = study_date;
//        this.class_detail_id = class_detail_id;
//        this.trainer_id = trainer_id;
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
//    public Date getStudy_date() {
//        return study_date;
//    }
//    public void setStudy_date(Date study_date) {
//        this.study_date = study_date;
//    }
//    public Long getClass_detail_id() {
//        return class_detail_id;
//    }
//    public void setClass_detail_id(Long class_detail_id) {
//        this.class_detail_id = class_detail_id;
//    }
//    public Long getTrainer_id() {
//        return trainer_id;
//    }
//    public void setTrainer_id(Long trainer_id) {
//        this.trainer_id = trainer_id;
//    }

}
