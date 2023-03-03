package com.FaTrainingSystem.service.dto;

import com.FaTrainingSystem.domain.Session;
import com.FaTrainingSystem.domain.Syllabus;
import com.FaTrainingSystem.domain.Unit;
import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * A DTO for the {@link Session} entity
 */
@Data
public class SessionDto implements Serializable {

  private Long id;
  private Integer index;
  private Syllabus syllabus;

  @Size(max = 45)
  private String status;

  @Size(max = 45)
  private String name;

  private Set<Unit> units;
}
