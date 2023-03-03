package com.FaTrainingSystem.service.dto;

import com.FaTrainingSystem.domain.Delivery;
import com.FaTrainingSystem.domain.Lesson;
import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * A DTO for the {@link Delivery} entity
 */
@Data
public class DeliveryDto implements Serializable {

  private Long id;

  @Size(max = 50)
  private String name;

  private Set<Lesson> lessons;
}
