package com.FaTrainingSystem.service.dto;

import com.FaTrainingSystem.domain.ManagerClass;
import com.FaTrainingSystem.domain.User;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * A DTO for the {@link ManagerClass} entity
 */
@Data
public class ManagerClassDto implements Serializable {

  private Integer id;

  @NotNull
  private User user;

  @NotNull
  private ClassDetailDto cd;
}
