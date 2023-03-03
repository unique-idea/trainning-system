package com.FaTrainingSystem.service.dto;

import com.FaTrainingSystem.domain.Level;
import com.FaTrainingSystem.domain.Syllabus;
import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * A DTO for the {@link Level} entity
 */
@Data
public class LevelDto implements Serializable {

  private Long id;

  @Size(max = 50)
  private String name;

  private Set<Syllabus> syllabi;
}
