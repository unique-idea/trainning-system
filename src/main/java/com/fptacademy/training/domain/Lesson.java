package com.fptacademy.training.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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

  @Column(length = 100)
  private String name;

  @Column(length = 45)
  @JsonIgnore
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
  @JsonIgnore
  @JoinColumn(name = "unit_id")
  private Unit unit;

  @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
  private List<Material> materials;
}
