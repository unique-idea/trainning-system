package com.fptacademy.training.domain;

import com.fptacademy.training.domain.enumeration.SyllabusStatus;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "syllabuses")
@Entity
public class Syllabus extends AbstractAuditEntity implements Serializable {
  private static final Long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 100)
  private String name;

  @Column(length = 20)
  private String code;

  private Integer attendeeNumber;

  @Column(length = 20)
  @Enumerated(EnumType.STRING)
  private SyllabusStatus status;

  private Integer duration;

  private Float version;

  @Column(columnDefinition = "TEXT")
  private String technicalRequirement;

  @Column(columnDefinition = "TEXT")
  private String courseObjective;

  @Column(columnDefinition = "TEXT")
  private String trainingPrinciple;

  @ManyToOne
  @JoinColumn(name = "level_id")
  private Level level;

  @OneToMany(mappedBy = "syllabus")
  private List<Session> sessions = new ArrayList<>();

  @OneToOne
  @JoinColumn(name = "assessment_id")
  private Assessment assessment;
}
