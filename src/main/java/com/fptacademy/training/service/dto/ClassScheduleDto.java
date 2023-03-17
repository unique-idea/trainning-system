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
public class ClassScheduleDto implements Serializable{
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

}
