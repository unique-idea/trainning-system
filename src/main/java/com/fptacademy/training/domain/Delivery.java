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
@Table(name = "deliveries")
@Entity
public class Delivery implements Serializable {
    private static final Long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String name;
}
