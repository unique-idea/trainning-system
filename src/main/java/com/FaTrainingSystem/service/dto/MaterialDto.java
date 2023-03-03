package com.FaTrainingSystem.service.dto;

import com.FaTrainingSystem.domain.Material;
import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * A DTO for the {@link Material} entity
 */
@Data
public class MaterialDto implements Serializable {

  private Long id;
  private byte[] uploadFile;

  @Size(max = 50)
  private String createdBy;

  private LocalDate createdDate;
  private LessonDto lesson;
}
