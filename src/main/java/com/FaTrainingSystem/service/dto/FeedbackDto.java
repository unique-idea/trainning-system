package com.FaTrainingSystem.service.dto;

import com.FaTrainingSystem.domain.Feedback;
import com.FaTrainingSystem.domain.User;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * A DTO for the {@link Feedback} entity
 */
@Data
public class FeedbackDto implements Serializable {

  private Integer id;

  @NotNull
  private User user;

  @NotNull
  private Long classId;

  @NotNull
  private Long programId;

  private String description;
  private byte[] uploadFile;

  @Size(max = 50)
  private String status;
}
