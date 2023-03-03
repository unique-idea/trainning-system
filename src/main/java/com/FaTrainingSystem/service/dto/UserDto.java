package com.FaTrainingSystem.service.dto;

import com.FaTrainingSystem.domain.User;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * A DTO for the {@link User} entity
 */
@Data
public class UserDto implements Serializable {

  private String id;

  @NotNull
  private Long levelId;

  @Size(max = 100)
  @NotNull
  private String fullName;

  @Size(max = 50)
  @NotNull
  private String email;

  @Size(max = 50)
  @NotNull
  private String password;

  @NotNull
  private LocalDate dob;

  @NotNull
  private Byte gender;

  @Size(max = 10)
  @NotNull
  private String role;

  @NotNull
  private Byte status;

  private byte[] avatar;
  private Set<ClassScheduleDto> classSchedulesAdmin;
  private Set<ClassScheduleDto> classSchedulesTrainer;
  private Set<ClassDto> classFields;
  private Set<TraineeClassDto> traineeClasses;
  private Set<ManagerClassDto> managerClasses;
  private Set<ProgramDto> programsCreateBy;
  private Set<ProgramDto> programsModifiedBy;
  private Set<FeedbackDto> feedbacks;
}
