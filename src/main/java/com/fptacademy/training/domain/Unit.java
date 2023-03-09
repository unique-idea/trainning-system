package com.fptacademy.training.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "units")
@Entity
public class Unit implements Serializable {

  private static final Long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(max = 100)
  @Column(length = 100)
  private String title;

  @Size(max = 45)
  @Column(length = 45)
  private String status;

  @Size(max = 100)
  @Column(length = 100)
  private String name;

  @Column(name = "`index`")
  private Integer index;

  @OneToMany(mappedBy = "unit")
  private List<Lesson> lessons;

  @ManyToOne
  @JoinColumn(name = "session_id")
  private Session session;
}
