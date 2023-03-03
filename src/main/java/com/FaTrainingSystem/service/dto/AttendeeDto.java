package com.FaTrainingSystem.service.dto;

import com.FaTrainingSystem.domain.Attendee;
import com.FaTrainingSystem.domain.ClassDetail;
import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * A DTO for the {@link Attendee} entity
 */
@Data
public class AttendeeDto implements Serializable {

  private Integer id;

  @Size(max = 50)
  private String attendeeType;

  private Set<ClassDetail> classDetails;
}
