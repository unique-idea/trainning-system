package com.FaTrainingSystem.service.dto;

import com.FaTrainingSystem.domain.Lesson;
import com.FaTrainingSystem.domain.Material;
import com.FaTrainingSystem.domain.OutputStandard;
import com.FaTrainingSystem.domain.Unit;
import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * A DTO for the {@link Lesson} entity
 */
@Data
public class LessonDto implements Serializable {

  private Long id;
  private Unit unit;
  private OutputStandard outputStandard;
  private DeliveryDto delivery;

  @Size(max = 100)
  private String name;

  private Integer duration;
  private FormatTypeDto formatType;

  @Size(max = 45)
  private String status;

  private Set<Material> materials;
}
