package com.fptacademy.training.domain;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Table(name = "deliveries")
@Entity
public class Delivery implements Serializable {

  private static final Long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 50)
  private String name;

  private String code;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Transient
  private Double present;
}
