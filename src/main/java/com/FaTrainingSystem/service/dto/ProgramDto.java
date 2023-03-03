package com.FaTrainingSystem.service.dto;

import com.FaTrainingSystem.domain.Program;
import com.FaTrainingSystem.domain.ProgramSyllabus;
import com.FaTrainingSystem.domain.User;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * A DTO for the {@link Program} entity
 */
@Data
public class ProgramDto implements Serializable {

  private Integer id;

  @NotNull
  private User createdBy;

  @NotNull
  private User modifiedBy;

  @Size(max = 50)
  @NotNull
  private String name;

  @NotNull
  private LocalDate createdOn;

  @NotNull
  private LocalDate modifiedDate;

  @NotNull
  private Integer duration;

  @NotNull
  private Boolean status;

  private Set<ProgramSyllabus> programSyllabi;
  private Set<ClassDto> classFields;
}
