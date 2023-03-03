package com.FaTrainingSystem.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "class_detail")
public class ClassDetail implements Serializable {
    private static final long serialVersionUID = -8729895668216841487L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "class_id", nullable = false)
    private Class classField;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attendee_id", nullable = false)
    private Attendee attendee;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @Size(max = 50)
    @Column(name = "training_program", length = 50)
    private String trainingProgram;

    @Size(max = 50)
    @Column(name = "attendee_type", length = 50)
    private String attendeeType;

    @Size(max = 50)
    @Column(name = "fsu", length = 50)
    private String fsu;

    @Size(max = 50)
    @Column(name = "class_location", length = 50)
    private String classLocation;

    @Size(max = 50)
    @Column(name = "others", length = 50)
    private String others;

    @Column(name = "planned")
    private Integer planned;

    @Column(name = "accepted")
    private Integer accepted;

    @Column(name = "actual")
    private Integer actual;

    @Column(name = "start_at")
    private LocalTime startAt;

    @Column(name = "finish_at")
    private LocalTime finishAt;

    @OneToMany(mappedBy = "cd")
    private Set<TraineeClass> traineeClasses = new LinkedHashSet<>();

    @OneToMany(mappedBy = "cd")
    private Set<ManagerClass> managerClasses = new LinkedHashSet<>();

}