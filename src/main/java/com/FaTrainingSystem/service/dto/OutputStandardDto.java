package com.FaTrainingSystem.service.dto;

import com.FaTrainingSystem.domain.OutputStandard;
import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * A DTO for the {@link OutputStandard} entity
 */
@Data
public class OutputStandardDto implements Serializable {

  private Long id;

  @Size(max = 10)
  private String name;

  private Set<LessonDto> lessons;
}
