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
@Table(name = "output_standards")
@Entity
public class OutputStandard implements Serializable {
    private static final Long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 10)
    @Column(length = 10, nullable = false, unique = true)
    private String name;
}
