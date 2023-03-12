package com.fptacademy.training.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Table(name = "format_types")
@Entity
public class FormatType implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 20)
    @Column(length = 20, nullable = false, unique = true)
    private String name;
}
