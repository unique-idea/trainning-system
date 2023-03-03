package com.FaTrainingSystem.service.dto;

import com.FaTrainingSystem.domain.ClassSyllabus;
import com.FaTrainingSystem.domain.Syllabus;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * A DTO for the {@link ClassSyllabus} entity
 */
@Data
public class ClassSyllabusDto implements Serializable {

  private Integer id;

  @NotNull
  private Syllabus syllabus;

  @NotNull
  private ClassDto classField;
}
