package com.FaTrainingSystem.service.dto;

import com.FaTrainingSystem.domain.Syllabus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * A DTO for the {@link Syllabus} entity
 */
@Data
public class SyllabusDto implements Serializable {

  private Long id;

  @Size(max = 100)
  private String name;

  @Size(max = 20)
  private String code;

  private LocalDate createdDate;

  @Size(max = 50)
  private String createdBy;

  @Size(max = 50)
  private String modifiedBy;

  private LocalDate modifiedDate;

  @Size(max = 100)
  private String title;

  private Integer attendeeNumber;
  private String technicalRequirement;

  @Size(max = 20)
  private String status;

  private LevelDto level;
  private String courseObjective;
  private String trainingPrinciple;
  private Set<ProgramSyllabusDto> programSyllabi;
  private Set<SessionDto> sessions;
  private Set<ClassSyllabusDto> classSyllabi;
  private AssessmentDto assessment;
}
