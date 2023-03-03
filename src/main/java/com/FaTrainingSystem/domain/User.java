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
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = -8738384462004648046L;
    @Id
    @Column(name = "idUser", nullable = false, length = 10)
    private String id;

    @NotNull
    @Column(name = "level_id", nullable = false)
    private Long levelId;

    @Size(max = 100)
    @NotNull
    @Column(name = "fullName", nullable = false, length = 100)
    private String fullName;

    @Size(max = 50)
    @NotNull
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Size(max = 50)
    @NotNull
    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @NotNull
    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @NotNull
    @Column(name = "gender", nullable = false)
    private Byte gender;

    @Size(max = 10)
    @NotNull
    @Column(name = "role", nullable = false, length = 10)
    private String role;

    @NotNull
    @Column(name = "status", nullable = false)
    private Byte status;

    @Column(name = "avatar")
    private byte[] avatar;

    @OneToMany(mappedBy = "admin")
    private Set<ClassSchedule> classSchedulesAdmin = new LinkedHashSet<>();

    @OneToMany(mappedBy = "trainer")
    private Set<ClassSchedule> classSchedulesTrainer = new LinkedHashSet<>();

    @OneToMany(mappedBy = "createdBy")
    private Set<Class> classFields = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<TraineeClass> traineeClasses = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<ManagerClass> managerClasses = new LinkedHashSet<>();

    @OneToMany(mappedBy = "createdBy")
    private Set<Program> programsCreateBy = new LinkedHashSet<>();

    @OneToMany(mappedBy = "modifiedBy")
    private Set<Program> programsModifiedBy = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Feedback> feedbacks = new LinkedHashSet<>();

}