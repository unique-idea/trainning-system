package com.FaTrainingSystem.service.dto;

import com.FaTrainingSystem.domain.Unit;
import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * A DTO for the {@link Unit} entity
 */
@Data
public class UnitDto implements Serializable {

  private Long id;

  @Size(max = 100)
  private String name;

  @Size(max = 100)
  private String title;

  private Integer index;

  @Size(max = 45)
  private String status;

  private SessionDto session;
  private Set<LessonDto> lessons;
}
