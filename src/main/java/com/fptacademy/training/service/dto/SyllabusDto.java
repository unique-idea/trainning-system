package com.fptacademy.training.service.dto;

import com.fptacademy.training.domain.OutputStandard;
import com.fptacademy.training.domain.enumeration.SyllabusStatus;
import java.time.Instant;
import java.util.List;
import lombok.Data;

@Data
public class SyllabusDto {

  private Long id;

  @Data
  public static class SyllabusListDto {

    private Long id;

    private String name;

    private String code;

    private Integer attendeeNumber;

    private SyllabusStatus status;

    private String technicalRequirement;

    private String courseObjective;

    private String trainingPrinciple;

    private Instant createdAt;

    private String createdBy;

    private Integer duration;

    private List<OutputStandard> sessions;
  }
}
