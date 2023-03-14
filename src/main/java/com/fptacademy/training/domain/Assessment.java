package com.fptacademy.training.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@ToString
@Table(name = "assessments")
@Entity
public class Assessment implements Serializable {
    private static final Long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(precision = 3, scale = 2)
    private Float quiz;
    @Column(precision = 3, scale = 2)
    private Float assignment;
    @Column(name = "final", precision = 3, scale = 2)
    private Float finalField;
    @Column(precision = 3, scale = 2)
    private Float finalPractice;
    @Column(precision = 3, scale = 2)
    private Float finalTheory;
    @Column(precision = 3, scale = 2)
    private Float gpa;
}
