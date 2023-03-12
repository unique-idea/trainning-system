package com.fptacademy.training.domain;

import com.fptacademy.training.domain.enumeration.SyllabusStatus;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
import lombok.ToString;

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

  @Size(max = 100)
  @Column(length = 100, nullable = false)
  private String name;

  @Size(max = 20)
  @Column(length = 20, unique = true, nullable = false)
  private String code;

  private Integer attendeeNumber;

  @Size(max = 20)
  @Column(length = 20)
  @Enumerated(EnumType.STRING)
  private SyllabusStatus status;

  private Integer duration;

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
}
