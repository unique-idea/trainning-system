package com.FaTrainingSystem.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "attendee")
public class Attendee implements Serializable {
    private static final long serialVersionUID = 3910300211690920332L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAttendee", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "attendee_type", length = 50)
    private String attendeeType;

    @OneToMany(mappedBy = "attendee")
    private Set<ClassDetail> classDetails = new LinkedHashSet<>();

}