package com.fptacademy.training.domain;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "class_details")
@Entity
public class ClassDetail implements Serializable {
    private static final Long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "class_id", nullable = false)
    private Class classField;
    @Column(length = 20, nullable = false)
    private String status;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
    @ManyToOne
    @JoinColumn(name = "attendee_id")
    private Attendee attendee;
    private Integer planned;
    private Integer accepted;
    private Integer actual;
    private LocalTime startAt;
    private LocalTime finishAt;
    @Column(length = 200)
    private String others;
    @OneToMany(mappedBy = "classDetail")
    private List<ClassSchedule> schedules;
    @ManyToMany(fetch = FetchType.EAGER)            /*Add fetch eager here*/
    @JoinTable(
            name = "user_class_detail",
            joinColumns = {@JoinColumn(name = "class_detail_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> users;
    private String detailLocation;
    @Email
    private String contactPoint;
}
