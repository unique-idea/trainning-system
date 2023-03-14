package com.fptacademy.training.service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class LessonDTO implements Serializable {

    private static final Long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String status;

    private Integer duration;

}
