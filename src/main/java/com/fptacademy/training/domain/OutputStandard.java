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
@Table(name = "output_standards")
@Entity
public class OutputStandard implements Serializable {

  private static final Long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 10)
  private String name;

  private String code;

  @Column(columnDefinition = "TEXT")
  private String description;
}
