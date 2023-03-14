package com.fptacademy.training.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Table(name = "class_schedules")
@Entity
public class ClassSchedule implements Serializable {
    private static final Long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "class_detail_id")
    private ClassDetail classDetail;
    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private User trainer;
    private LocalDate studyDate;
}
