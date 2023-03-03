package com.FaTrainingSystem.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
@Table(name = "class")
public class Class implements Serializable {
    private static final long serialVersionUID = -7869203083951525503L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Size(max = 50)
    @NotNull
    @Column(name = "class_name", nullable = false, length = 50)
    private String className;

    @Size(max = 50)
    @NotNull
    @Column(name = "class_code", nullable = false, length = 50)
    private String classCode;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @Column(name = "duration")
    private Integer duration;

    @OneToMany(mappedBy = "classField")
    private Set<ClassSchedule> classSchedules = new LinkedHashSet<>();

    @OneToMany(mappedBy = "classField")
    private Set<ClassSyllabus> classSyllabi = new LinkedHashSet<>();

    @OneToMany(mappedBy = "classField")
    private Set<ClassDetail> classDetails = new LinkedHashSet<>();

}