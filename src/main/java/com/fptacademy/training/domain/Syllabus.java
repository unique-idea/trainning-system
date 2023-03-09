package com.fptacademy.training.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
@Table(name = "syllabuses")
@Entity
public class Syllabus extends AbstractAuditEntity implements Serializable {
    private static final Long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 100)
    @Column(length = 100, nullable = false)
    private String name;
    @Size(max = 20)
    @Column(length = 20, unique = true, nullable = false)
    private String code;
    private Integer attendeeNumber;
    @Size(max = 20)
    @Column(length = 20)
    private String status;
    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;
    @Column(columnDefinition = "TEXT")
    private String technicalRequirement;
    @Column(columnDefinition = "TEXT")
    private String courseObjective;
    @Column(columnDefinition = "TEXT")
    private String trainingPrinciple;
    @OneToMany(mappedBy = "syllabus")
    private List<Session> sessions;
}
