package com.fptacademy.training.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Setter
@Getter
@ToString
@Table(name = "sessions")
@Entity
public class Session implements Serializable {
    private static final Long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "syllabus_id")
    private Syllabus syllabus;
    @Column(name = "`index`")
    private Integer index;
    @Size(max = 45)
    @Column(length = 45)
    private String name;
    @Size(max = 45)
    @Column(length = 45)
    private String status;
}
