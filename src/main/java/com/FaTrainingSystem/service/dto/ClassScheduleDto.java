package com.FaTrainingSystem.service.dto;

import com.FaTrainingSystem.domain.ClassSchedule;
import com.FaTrainingSystem.domain.User;
import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * A DTO for the {@link ClassSchedule} entity
 */
@Data
public class ClassScheduleDto implements Serializable {

  private Integer id;

  @NotNull
  private ClassDto classField;

  @NotNull
  private User trainer;

  @NotNull
  private User admin;

  private LocalDate studyDay;

  @Size(max = 100)
  private String branchLocation;
}
