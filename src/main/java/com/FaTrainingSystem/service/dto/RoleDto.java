package com.FaTrainingSystem.service.dto;

import com.FaTrainingSystem.domain.Role;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * A DTO for the {@link Role} entity
 */
@Data
public class RoleDto implements Serializable {

  private Integer id;

  @Size(max = 100)
  @NotNull
  private String roleName;
}
