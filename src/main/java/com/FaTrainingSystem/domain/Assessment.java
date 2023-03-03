package com.FaTrainingSystem.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "assessment")
public class Assessment implements Serializable {
    private static final long serialVersionUID = -6737354878665698403L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
    private Syllabus syllabus;

    @Column(name = "quiz")
    private Float quiz;

    @Column(name = "assignment")
    private Float assignment;

    @Column(name = "final")
    private Float finalField;

    @Column(name = "final_theory")
    private Float finalTheory;

    @Column(name = "final_practice")
    private Float finalPractice;

    @Column(name = "gpa")
    private Float gpa;

}