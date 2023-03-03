package com.FaTrainingSystem.service.dto;

import com.FaTrainingSystem.domain.Syllabus;
import java.io.Serializable;
import lombok.Data;

/**
 * A DTO for the {@link com.FaTrainingSystem.domain.Assessment} entity
 */
@Data
public class AssessmentDto implements Serializable {

  private Long id;
  private Syllabus syllabus;
  private Float quiz;
  private Float assignment;
  private Float Field;
  private Float Theory;
  private Float Practice;
  private Float gpa;
}
