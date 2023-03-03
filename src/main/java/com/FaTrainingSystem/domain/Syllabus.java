package com.FaTrainingSystem.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "syllabus")
public class Syllabus implements Serializable {
    private static final long serialVersionUID = -5578254788436975417L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @Size(max = 20)
    @Column(name = "code", length = 20)
    private String code;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Size(max = 50)
    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Size(max = 50)
    @Column(name = "modified_by", length = 50)
    private String modifiedBy;

    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    @Size(max = 100)
    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "attendee_number")
    private Integer attendeeNumber;

    @Lob
    @Column(name = "technical_requirement")
    private String technicalRequirement;

    @Size(max = 20)
    @Column(name = "status", length = 20)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id")
    private Level level;

    @Lob
    @Column(name = "course_objective")
    private String courseObjective;

    @Lob
    @Column(name = "training_principle")
    private String trainingPrinciple;

    @OneToMany(mappedBy = "syllabus")
    private Set<ProgramSyllabus> programSyllabi = new LinkedHashSet<>();

    @OneToMany(mappedBy = "syllabus")
    private Set<Session> sessions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "syllabus")
    private Set<ClassSyllabus> classSyllabi = new LinkedHashSet<>();

    @OneToOne(mappedBy = "syllabus")
    private Assessment assessment;

}