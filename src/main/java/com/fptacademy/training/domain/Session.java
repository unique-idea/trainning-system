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
@Table(name = "sessions")
@Entity
public class Session implements Serializable {

  private static final Long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "`index`")
  private Integer index;

  @Size(max = 45)
  @Column(length = 45)
  private String name;

  @Size(max = 45)
  @Column(length = 45)
  private String status;

  @OneToMany(mappedBy = "session")
  private List<Unit> units;

  @ManyToOne
  @JoinColumn(name = "syllabus_id")
  private Syllabus syllabus;
}
