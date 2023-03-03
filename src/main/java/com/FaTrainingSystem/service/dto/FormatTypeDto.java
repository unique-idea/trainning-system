package com.FaTrainingSystem.service.dto;

import com.FaTrainingSystem.domain.FormatType;
import com.FaTrainingSystem.domain.Lesson;
import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * A DTO for the {@link FormatType} entity
 */
@Data
public class FormatTypeDto implements Serializable {

  private Long id;

  @Size(max = 20)
  private String name;

  private Set<Lesson> lessons;
}
