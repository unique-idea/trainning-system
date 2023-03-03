package com.FaTrainingSystem.service.dto;

import com.FaTrainingSystem.domain.ProgramSyllabus;
import com.FaTrainingSystem.domain.Syllabus;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * A DTO for the {@link ProgramSyllabus} entity
 */
@Data
public class ProgramSyllabusDto implements Serializable {

  private Integer id;

  @NotNull
  private Syllabus syllabus;

  @NotNull
  private ProgramDto program;
}
