package com.FaTrainingSystem.service.dto;

import com.FaTrainingSystem.domain.Status;
import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * A DTO for the {@link Status} entity
 */
@Data
public class StatusDto implements Serializable {

  private Integer id;

  @Size(max = 50)
  private String name;

  private Set<ClassDetailDto> classDetails;
}
