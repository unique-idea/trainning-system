package com.FaTrainingSystem.service.dto;

import com.FaTrainingSystem.domain.UserPermission;
import java.io.Serializable;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * A DTO for the {@link UserPermission} entity
 */
@Data
public class UserPermissionDto implements Serializable {

  private Integer id;

  @Size(max = 30)
  private String roleName;

  @Size(max = 30)
  private String object;

  @Size(max = 30)
  private String permission;
}
