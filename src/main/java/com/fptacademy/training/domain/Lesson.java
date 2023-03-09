package com.fptacademy.training.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Table(name = "lessons")
@Entity
public class Lesson implements Serializable {

  private static final Long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(max = 100)
  @Column(length = 100)
  private String name;

  @Size(max = 45)
  @Column(length = 45)
  private String status;

  private Integer duration;

  @ManyToOne
  @JoinColumn(name = "output_standard_id")
  private OutputStandard outputStandard;

  @ManyToOne
  @JoinColumn(name = "format_type_id")
  private FormatType formatType;

  @ManyToOne
  @JoinColumn(name = "delivery_id")
  private Delivery delivery;

  @ManyToOne
  @JoinColumn(name = "unit_id")
  private Unit unit;

  @OneToMany(mappedBy = "lesson")
  private List<Material> materials;
}
