package com.FaTrainingSystem.service.dto;

import com.FaTrainingSystem.domain.TraineeClass;
import com.FaTrainingSystem.domain.User;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * A DTO for the {@link TraineeClass} entity
 */
@Data
public class TraineeClassDto implements Serializable {

  private Integer id;

  @NotNull
  private User user;

  @NotNull
  private ClassDetailDto cd;
}
