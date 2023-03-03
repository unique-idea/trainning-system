package com.FaTrainingSystem.service.dto;

import com.FaTrainingSystem.domain.*;
import com.FaTrainingSystem.domain.Class;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * A DTO for the {@link Class} entity
 */
@Data
public class ClassDto implements Serializable {

  private Integer id;

  @NotNull
  private Program program;

  @NotNull
  private User createdBy;

  @Size(max = 50)
  @NotNull
  private String className;

  @Size(max = 50)
  @NotNull
  private String classCode;

  private LocalDate createdOn;
  private Integer duration;
  private Set<ClassSchedule> classSchedules;
  private Set<ClassSyllabus> classSyllabi;
  private Set<ClassDetail> classDetails;
}
