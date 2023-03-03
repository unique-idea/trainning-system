package com.FaTrainingSystem.service.dto;

import com.FaTrainingSystem.domain.ClassDetail;
import com.FaTrainingSystem.domain.ManagerClass;
import com.FaTrainingSystem.domain.Status;
import com.FaTrainingSystem.domain.TraineeClass;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * A DTO for the {@link ClassDetail} entity
 */
@Data
public class ClassDetailDto implements Serializable {

  private Integer id;

  @NotNull
  private ClassDto classField;

  @NotNull
  private AttendeeDto attendee;

  @NotNull
  private Status status;

  @Size(max = 50)
  private String trainingProgram;

  @Size(max = 50)
  private String attendeeType;

  @Size(max = 50)
  private String fsu;

  @Size(max = 50)
  private String classLocation;

  @Size(max = 50)
  private String others;

  private Integer planned;
  private Integer accepted;
  private Integer actual;
  private LocalTime startAt;
  private LocalTime finishAt;
  private Set<TraineeClass> traineeClasses;
  private Set<ManagerClass> managerClasses;
}
