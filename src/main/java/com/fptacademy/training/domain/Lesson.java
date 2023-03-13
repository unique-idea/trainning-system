package com.fptacademy.training.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
