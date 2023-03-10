package com.fptacademy.training.service.dto;

import com.fptacademy.training.domain.OutputStandard;
import com.fptacademy.training.domain.Session;
import com.fptacademy.training.domain.enumeration.SyllabusStatus;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SyllabusDto {

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

  private List<OutputStandard> outputStandard = new ArrayList<>();

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
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

    private List<OutputStandard> outputStandard = new ArrayList<>();
  }

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class OutputStandardDto {

    private Long id;
    private String name;
  }
}
