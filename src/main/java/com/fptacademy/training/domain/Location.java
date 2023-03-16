package com.fptacademy.training.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@Table(name = "locations")
@Entity
public class Location implements Serializable {
    private static final Long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 45)
    @Column(length = 45, nullable = false)
    private String city;
    @Size(max = 45)
    @Column(length = 45, nullable = false)
    private String fsu;

    @Size(max = 45)
    @Column(length = 45, nullable = false)
    private String code;
}
